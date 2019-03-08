package xyz.borsay.yourfavoritebooks;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private BookListActivity mActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView bookTitle;
        public AppCompatTextView bookAuthor;
        public  AppCompatTextView bookGenre;
        public ConstraintLayout bookBox;
        CheckBox deleteBook;
        ImageButton updateBook;


        public ViewHolder(View itemView) {
            super(itemView);
            bookTitle =  itemView.findViewById(R.id.bookTitle);
            bookAuthor =  itemView.findViewById(R.id.bookAuthor);
            bookGenre =  itemView.findViewById(R.id.bookGenre);
            bookBox = itemView.findViewById(R.id.bookBox);
            deleteBook = itemView.findViewById(R.id.deleteBook);
            updateBook = itemView.findViewById(R.id.updateBook);

        }
    }


    private OrderedRealmCollection<BookInfo> mBookList;

    public BookListAdapter(BookListActivity activity,OrderedRealmCollection<BookInfo> books) {
        this.mBookList = books;
        this.mActivity = activity;
    }


    @Override
    public BookListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.book_info, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(BookListAdapter.ViewHolder viewHolder, int mPosition) {
        BookInfo aBook = mBookList.get(mPosition);

        viewHolder.bookTitle.setText(aBook.getBookTitle());

        //If no author listed then n/a
        viewHolder.bookAuthor.setText(aBook.getAuthorsNames().isEmpty()
                ? "N/A" : aBook.getAuthorsNames());

        viewHolder.bookGenre.setText(Constants.genreMap.get(aBook.getGenre()));

        viewHolder.deleteBook.setChecked(aBook.isDelete());
        viewHolder.deleteBook.setTag(mPosition);
        viewHolder.updateBook.setTag(mPosition);
        viewHolder.deleteBook.setOnClickListener(listener);



    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            BookInfo mBookInfo = mBookList.get(position);
            mActivity.setDelete(mBookInfo.getBookId());
        }
    };




    @Override
    public int getItemCount() {
        return mBookList.size();
    }



}
