package com.example.yongjaenam.surewhynot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ImageView;


public class VisionActivity extends AppCompatActivity{
    ImageView ivv;
    int cnt=0;
    String sss=null;
    private NaverTTSTask mNaverTTSTask;
    String[] mTextString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vision);
        ivv = (ImageView) findViewById(R.id.HI);
        sss = "상품을 카메라로 찍어주십쇼. 볼륨 버튼을 누르면 상품이 찍힙니다.";
        mTextString = new String[]{sss};
        mNaverTTSTask = new NaverTTSTask();
        mNaverTTSTask.execute(mTextString);
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        ivv.setImageURI(data.getData());

        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),1);
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


        }else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

        }

        return true;
        //return super.onKeyDown(keyCode, event);
    }



}
