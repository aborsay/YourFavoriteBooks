package xyz.borsay.yourfavoritebooks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;

/**
 * Edits or adds books to the Realm database
 * Aaron Borsay 3/8/2019
 */
public class EditOrAddBookActivity extends MasterActivity {

    private ArrayList<HashMap<String,String>> mCurrentTypes;
    private  HashMap<String,String> mSelectedGenre;
    private BookInfo mBookInfo;
    private Realm mRealm;

    /**
     * This could likely be subbed in to different functions
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_or_add_book);
        appData.setCurrentSection(2);
        int positionCount=0;
        String mGenrePosition ="";
        mRealm = Realm.getDefaultInstance();

        if(!appData.getCurrentBookId().isEmpty()){
            mBookInfo = mRealm.where(BookInfo.class).equalTo("mBookId",
                    appData.getCurrentBookId()).findFirst();
            ((EditText)findViewById(R.id.bookTitle)).setText(mBookInfo.getBookTitle(),
                    TextView.BufferType.EDITABLE);
            ((EditText)findViewById(R.id.bookAuthor)).setText(mBookInfo.getAuthorsNames(),
                    TextView.BufferType.EDITABLE);
            mGenrePosition = mBookInfo.getGenre();
        }

        Spinner mSpinner;
        mSpinner = (Spinner) findViewById(R.id.bookGenre);
        ArrayAdapter<String> mAdapter;
        ArrayList<String> mHelpArray = new ArrayList<>();

        mSelectedGenre = new HashMap<>();
        mCurrentTypes = new ArrayList<>();
        mCurrentTypes.add(new HashMap<String, String>());
        mCurrentTypes.get(0).put("position", "0");
        mCurrentTypes.get(0).put("genre","choose sub-category");
        mHelpArray.add("̶ choose sub-category ̶");
        for (Map.Entry<String, String> entry : Constants.genreMap.entrySet()) {
            mCurrentTypes.add(new HashMap<String, String>());
            int count = mCurrentTypes.size() - 1;
            mCurrentTypes.get(count).put("position", entry.getKey());
            if(!mGenrePosition.isEmpty()){
                if(mCurrentTypes.get(count).get("position").equals(mGenrePosition)){
                    positionCount =count;
                }
            }
            mCurrentTypes.get(count).put("genre",entry.getValue());

            mHelpArray.add(entry.getValue());

        }

        mAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, mHelpArray);
        mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        mSpinner.setAdapter(mAdapter);

        if(!mGenrePosition.isEmpty()){
            mSpinner.setSelection(positionCount);
            mSelectedGenre = new HashMap<>();
            mSelectedGenre.put("position",mCurrentTypes.get(positionCount).get("position"));
            mSelectedGenre.put("genre",     mCurrentTypes.get(positionCount).get("genre"));
        }else {
            mSpinner.setSelection(0, false);
        }
        mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                mSelectedGenre = new HashMap<>();
                mSelectedGenre.put("position",mCurrentTypes.get(position).get("position"));
                mSelectedGenre.put("genre",     mCurrentTypes.get(position).get("genre"));

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

                mSelectedGenre = new HashMap<>();
                mSelectedGenre.put("position",mCurrentTypes.get(0).get("position"));
                mSelectedGenre.put("genre",     mCurrentTypes.get(0).get("genre"));
            }
        });

    }

    /**
     * Updates previous book or adds a new book and goes back to main list.
     * Test to makes sure at the very least a book title is included
     */
    private void update(){
        if(!((EditText)findViewById(R.id.bookTitle)).getText().toString().trim().isEmpty()){
            if(!appData.getCurrentBookId().isEmpty()){
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        BookInfo mBookInfo= realm.where(BookInfo.class).equalTo("mBookId",
                                appData.getCurrentBookId()).findFirst();
                        mBookInfo.setBookTitle(((EditText) findViewById(R.id.bookTitle))
                                .getText().toString());
                        mBookInfo.setAuthorsNames(((EditText) findViewById(R.id.bookAuthor))
                                .getText().toString());
                        mBookInfo.setGenre(mSelectedGenre.get("position"));
                    }
                });
                Intent main = new Intent(this,BookListActivity.class);
                startActivity(main);
                finish();
            }else{

                    mRealm.executeTransactionAsync(new Realm.Transaction() {
                       @Override
                       public void execute(Realm realm) {

                           BookInfo mBookInfo = realm.createObject(BookInfo.class,
                                   UUID.randomUUID().toString());
                           mBookInfo.setBookTitle(((EditText) findViewById(R.id.bookTitle))
                                   .getText().toString());
                           mBookInfo.setAuthorsNames(((EditText) findViewById(R.id.bookAuthor))
                                   .getText().toString());
                           mBookInfo.setGenre(mSelectedGenre.get("position"));
                       }
                   });
                    Intent main = new Intent(this,BookListActivity.class);
                    startActivity(main);
                    finish();

            }
        }else{
            new AlertDialog.Builder(EditOrAddBookActivity.this)
                    .setTitle("No Book Title")
                    .setMessage("You must at the very least have a book title")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).show();
        }
    }

    /**
     * called when update save button is click
     * @param view updateBook
     */
   public void updateBook(View view){
       update();
   }
}
