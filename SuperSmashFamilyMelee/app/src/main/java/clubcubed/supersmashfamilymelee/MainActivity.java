package clubcubed.supersmashfamilymelee;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    // private SharedPreferences savedPrefs;
    private MainPanel mainPanel;

    /**
     * lol
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // turns screen sideways
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // save preferences
        // jk no fuk dat
        // savedPrefs = getSharedPreferences( "MeleePrefs", MODE_PRIVATE );

        // gets dimensions
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Global.SCREEN_WIDTH = dm.widthPixels;
        Global.SCREEN_HEIGHT = dm.heightPixels;

        // switches dimensions if incorrect LMAO
        if (Global.SCREEN_WIDTH < Global.SCREEN_HEIGHT) {
            float temp = Global.SCREEN_WIDTH;
            Global.SCREEN_WIDTH = Global.SCREEN_HEIGHT;
            Global.SCREEN_HEIGHT = temp;
        }

        // asks for file access permissions
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        // creates the main panel
        mainPanel = new MainPanel(this);
        setContentView(mainPanel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainPanel.receiveBack();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mainPanel.receiveBack();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        mainPanel.receiveInput(motionEvent);
        return true;
    }
}
