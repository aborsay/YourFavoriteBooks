package xyz.borsay.yourfavoritebooks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Adapter to create list of books displayed in RecylerView using a holder layout of each
 * books info and options to edit or delete book
 */
public class IndividualBook extends RecyclerView.Adapter<IndividualBook.ViewHolder> {

    private ArrayList<BookInfo> mBookList;

    /**
     * creates the holder data for use to place each book into list.
     * Each holder represents one book.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * Inputs the list of books to be displayed on the screen from parent activity
     * @param mBookList list of books to be displaye
     */
    public IndividualBook(ArrayList<BookInfo> mBookList){
        this.mBookList = mBookList;
    }

    /**
     *
     * @param parent for creating the view of the holder
     * @param viewType not used
     * @return holder created for list
     */
    @Override
    public IndividualBook.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.individual_book, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    /**
     *
     * @param viewHolder holder of book info
     * @param mPosition position in book list
     */
    @Override
    public void onBindViewHolder(IndividualBook.ViewHolder viewHolder, int mPosition) {

    }

    /**
     *
     * @return mBookList.size()
     */
    @Override
    public int getItemCount() {
        return mBookList.size();
    }


}
