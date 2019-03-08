package xyz.borsay.yourfavoritebooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

public class MasterActivity extends AppCompatActivity {
    protected  YourFavoriteBooksApplication appData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData=((YourFavoriteBooksApplication) getApplicationContext());
    }


    protected void moveBackWords(){
        Intent main;

        int section = appData.getCurrentSection();
        if(section<=1){
            finishAffinity();
        }else{
            main = new Intent(getApplicationContext(),BookListActivity.class);
            startActivity(main);
            finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = true;
        if ((keyCode == KeyEvent.KEYCODE_BACK )) {
            moveBackWords();
        }
        return result;
    }
}
