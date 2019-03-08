package xyz.borsay.yourfavoritebooks;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class BookInfo extends RealmObject {


    @Required
    @PrimaryKey
    private  String mBookId;


    @Required
    private String mBookTitle;
    private String mAuthorsNames;
    private String mGenre;
    private boolean mDelete;

    public boolean isDelete() {
        return mDelete;
    }

    public void setDelete(boolean mDelete) {
        this.mDelete = mDelete;
    }

    public String getBookId() {
        return mBookId;
    }

    public void setBookId(String mBookId) {
        this.mBookId = mBookId;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public void setBookTitle(String mBookTitle) {
        this.mBookTitle = mBookTitle;
    }

    public String getAuthorsNames() {
        return mAuthorsNames;
    }

    public void setAuthorsNames(String mAuthorsNames) {
        this.mAuthorsNames = mAuthorsNames;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String mGenere) {
        this.mGenre = mGenere;
    }
}
