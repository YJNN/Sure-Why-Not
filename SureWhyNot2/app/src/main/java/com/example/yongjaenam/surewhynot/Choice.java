package com.example.yongjaenam.surewhynot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

public class Choice extends AppCompatActivity{

    //private long mShakeTime;
    //private static final int SHAKE_SKIP_TIME = 500;
    //private static final float SHAKE_THRESHOLD_GRAVITY = 2.7f;

    private NaverTTSTask mNaverTTSTask;
    String[] mTextString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choiceactivity);

        String A = "어서오세요! 편의점 안으로 들어오셨습니다. 진열대의 종류를 알고 싶으면 볼륨 상단키를, 개별 상품을 알고 싶으면 하단 키를 눌러주세요. ";
        mTextString = new String[]{A};
        mNaverTTSTask = new NaverTTSTask();
        mNaverTTSTask.execute(mTextString);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            startActivity(new Intent(this, NfcActivity.class));
        }else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            startActivity(new Intent(this, VisionActivity.class));
        }

        return true;
        //return super.onKeyDown(keyCode, event);
    }



}
