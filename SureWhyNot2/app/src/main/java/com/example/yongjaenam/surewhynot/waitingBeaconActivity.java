package com.example.yongjaenam.surewhynot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by Yongjae Nam on 2017-11-08.
 */

//처음 대기화면에서 이 화면으로 넘어오면, 비콘을 기다리는 액티비티.
public class waitingBeaconActivity extends AppCompatActivity{

    //private long mShakeTime;
    //private static final int SHAKE_SKIP_TIME = 500;
    //private static final float SHAKE_THRESHOLD_GRAVITY = 2.7f;
    private NaverTTSTask mNaverTTSTask;
    String[] mTextString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitingbeacon);

    }

    private class NaverTTSTask extends AsyncTask<String[], Void, String> {

        @Override
        protected String doInBackground(String[]... strings) {
            //여기서 서버에 요청
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
        if(event.getAction() == MotionEvent.ACTION_DOWN){ //눌렀을 때
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
