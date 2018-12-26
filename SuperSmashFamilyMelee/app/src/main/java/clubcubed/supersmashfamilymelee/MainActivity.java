package clubcubed.supersmashfamilymelee;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
        Global.SCREEN_HEIGHT = dm.heightPixels;
        Global.SCREEN_WIDTH = dm.widthPixels;

        // switches dimensions if incorrect LMAO
        if (Global.SCREEN_WIDTH < Global.SCREEN_HEIGHT) {
            float temp = Global.SCREEN_WIDTH;
            Global.SCREEN_WIDTH = Global.SCREEN_HEIGHT;
            Global.SCREEN_HEIGHT = temp;
        }

        Global.GAME_HEIGHT = 500f;
        Global.GAME_WIDTH = 750f;
        // refer to past conversation
        // so we dont need to talk about this again
        // note: its SCREEN_hight
        Global.GAME_RATIO = Global.SCREEN_HEIGHT / Global.GAME_HEIGHT;
        // trihard7 bars
        Global.GAME_DIFFERENCE = (Global.SCREEN_WIDTH - (Global.GAME_WIDTH*Global.GAME_RATIO)) /2;

        // asks for file access permissions
        /* probably NOT NEEDED for melee
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        */

//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, 1);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 1);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//        Global.BLUETOOTH_ADAPTER = BluetoothAdapter.getDefaultAdapter();

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
