package com.example.yongjaenam.surewhynot;

import android.content.Intent;
import android.graphics.Region;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;

/*import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;*/
/**
 * Created by Yongjae Nam on 2017-11-08.
 */
public class waitingBeaconActivity extends AppCompatActivity{
    //private long mShakeTime;
    //private static final int SHAKE_SKIP_TIME = 500;
    //private static final float SHAKE_THRESHOLD_GRAVITY = 2.7f;
    private NaverTTSTask mNaverTTSTask;
    //private BeaconManager beaconManager;
    private Region region;
    String[] mTextString;
    Boolean isConnected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitingbeacon);
    }
    
    /*@Override
    protected void onResume(){
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this); //블루투스 권한 및 활성화
        beaconManager.connect(new BeaconManager.ServiceReadyCallback(){
            @Override
            public void onServiceReady(){
                beaconManager.startRanging(region);
            }
        });
    }
    @Override
    protected void onPause(){
        //beaconManager.stopRanging(region);
        super.onPause();
    }*/

    private class NaverTTSTask extends AsyncTask<String[], Void, String> {
        @Override
        protected String doInBackground(String[]... strings) {
            APIExamTTS.main(mTextString, getFilesDir());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){ 
            startActivity(new Intent(this, Choice.class));
            finish();
        }
       return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

        }else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

        }

        return true;
    }
}
