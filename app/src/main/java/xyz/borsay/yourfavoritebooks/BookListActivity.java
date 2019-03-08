package xyz.borsay.yourfavoritebooks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import io.realm.Realm;
import io.realm.RealmResults;

/**
 * BookListActivity
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * Aaron Borsay 3/8/2019
 */
public class BookListActivity extends MasterActivity {

    private Realm mRealm;
    private RealmResults<BookInfo> mBookList;

    protected Handler deleteHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.book_list_activity);
        appData.setCurrentSection(1);
        mRealm = Realm.getDefaultInstance();

        mBookList = mRealm.where(BookInfo.class).findAll();
        mBookList = mBookList.sort("mBookTitle");

        placeBooks();
    }

    /**
     * Creates the list of books
     */
    private void placeBooks(){
        RecyclerView rvContacts =  findViewById(R.id.contactList);


        BookListAdapter contactsAdapter = new BookListAdapter(this,mBookList);
        // Attach the adapter to the RecyclerView to populate items
        rvContacts.setAdapter(contactsAdapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));


        rvContacts.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Drawable mDivider = ContextCompat.getDrawable(this, R.drawable.divider_list);
        DividerItemDecoration hItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);

        hItemDecoration.setDrawable(mDivider); //find different way
    }

    /**
     * Goes to edit add activity if Update button is clicked. Grabs ID of book to display info
     * in next activity
     * @param view updateBook
     */
    public void editBook(View view){
        BookInfo mBookInfo = mBookList.get((Integer)view.getTag());

        if(mBookInfo.getBookId()!=null) {
            appData.setCurrentBookId(mBookInfo.getBookId());
        }else{
            appData.setCurrentBookId("");
        }
        Intent main = new Intent(this,EditOrAddBookActivity.class);
        startActivity(main);
        finish();
    }

    /**
     * Goes to edit add activity if Update button is clicked. Creates a blank new book.
     * @param view addBook
     */
    public void addBook(View view){

        appData.setCurrentBookId("");
        Intent main = new Intent(this,EditOrAddBookActivity.class);
        startActivity(main);
        finish();
    }

    /**
     * If delete button is clicked goes to function to delete all marked books
     * @param view deleteBook
     */
    public void deleteBooks(View view){
        deleteAllDone();
    }

    /**
     * If check box is selected for a book it marks in database for eventual delete
     * @param bookID ID of book
     */
    public void setDelete(final String bookID){
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                BookInfo mBookInfo = realm.where(BookInfo.class)
                        .equalTo("mBookId", bookID).findFirst();
                mBookInfo.setDelete(!mBookInfo.isDelete());
            }
        });
    }


    /**
     * Deletes all of the book that are marked for deletion
     */
    private void deleteAllDone() {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(BookInfo.class).equalTo("mDelete", true)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });

        deleteHandler.postDelayed(processList, 1000);
    }


    /**
     * closes realm database when application is closed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }


    /**
     * time delay that was added when multiple books are deleted
     */
    protected Runnable processList = new Runnable() {
        @Override
        public void run() {
            placeBooks();
        }
    };
}
