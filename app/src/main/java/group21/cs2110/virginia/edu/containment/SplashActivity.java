package group21.cs2110.virginia.edu.containment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class SplashActivity extends Activity {

    private static final String TAG = SplashActivity.class.getName();
    private static final long DELAY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SplashActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //SplashActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private class IntentLauncher extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(DELAY * 1000);
            } catch(Exception e) {
                Log.e(TAG, e.getMessage());
            }

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}
