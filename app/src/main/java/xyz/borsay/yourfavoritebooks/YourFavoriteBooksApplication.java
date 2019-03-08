package xyz.borsay.yourfavoritebooks;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class YourFavoriteBooksApplication extends Application {

    private String mCurrentBookId;
    private int mCurrentSection=0;
    public YourFavoriteBooksApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("books.realm")
                .schemaVersion(0)
                //.migration(new Migration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public String getCurrentBookId() {
        return mCurrentBookId;
    }

    public void setCurrentBookId(String currentBookId) {
        this.mCurrentBookId = currentBookId;
    }


    /****************************************************************************
     * getCurrentSection()
     * helps to move Launch_Platform screen back to location where the person had last scrolled to
     * @return mCurrentSection
     */
    public int getCurrentSection(){
        return mCurrentSection;
    }
    /****************************************************************************
     * setCurrentSection(int mSection)
     * is used to help Activities_Platform set the section so the screen in Launch_Platform will
     * go back to previous location
     * @param   mSection    Current section app is in
     */
    public void setCurrentSection(int mSection){
        mCurrentSection=mSection;
    }
}
