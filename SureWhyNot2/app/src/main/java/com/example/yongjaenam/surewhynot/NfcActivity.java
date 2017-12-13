package com.example.yongjaenam.surewhynot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Yongjae Nam on 2017-11-08.
 */

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.nfc.*;
import android.nfc.tech.*;
import android.os.*;
import android.provider.*;
import android.app.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import java.util.Arrays;

public class NfcActivity extends AppCompatActivity {

    private NaverTTSTask mNaverTTSTask;
    String[] mTextString;
    String A;
    EditText etText;
    Button btTTS, btReset;

    String rfid = "";


    //TextView mTextView;
    NfcAdapter mNfcAdapter; // NFC 어댑터
    PendingIntent mPendingIntent; // 수신받은 데이터가 저장된 인텐트
    IntentFilter[] mIntentFilters; // 인텐트 필터
    String[][] mNFCTechLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfcactivity);
      //  etText = (EditText) findViewById(R.id.et_text);
      //  btTTS = (Button) findViewById(R.id.bt_tts);
      //  btReset = (Button) findViewById(R.id.bt_reset);
        String A = "NFC태그 모드입니다. 상품대에 있는 태그에 핸드폰을 찍어주세요.";

        mTextString = new String[]{A};
        mNaverTTSTask = new NaverTTSTask();
        mNaverTTSTask.execute(mTextString);
/*
        //버튼 클릭이벤트 - 클릭하면 에디터뷰에 있는 글자를 가져와서 네이버에 보낸다. MP3로 바꿔 달라고 ...
        btTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //사용자가 입력한 텍스트를 이 배열변수에 담는다.
                String mText;
                if (etText.getText().length() > 0) { //한글자 이상 1
                    mText = etText.getText().toString();
                    mTextString = new String[]{mText};

                    //AsyncTask 실행
                    mNaverTTSTask = new NaverTTSTask();
                    mNaverTTSTask.execute(mTextString);
                } else {
                    Toast.makeText(MainActivity.this, "텍스트를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        //리셋버튼
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etText.setText("");
                etText.setHint("텍스트를 입력하세요.");
            }
        });

*/

        // NFC 어댑터를 구한다
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // NFC 어댑터가 null 이라면 칩이 존재하지 않는 것으로 간주
        if (mNfcAdapter == null) {
            //mTextView.setText("This phone is not NFC enable.");
            return;
        }

        //mTextView.setText("Scan a NFC tag");

        // NFC 데이터 활성화에 필요한 인텐트를 생성
        Intent intent = new Intent(this, getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // NFC 데이터 활성화에 필요한 인텐트 필터를 생성
        IntentFilter iFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            iFilter.addDataType("*/*");
            mIntentFilters = new IntentFilter[]{iFilter};
        } catch (Exception e) {
            //mTextView.setText("Make IntentFilter error");
        }
        mNFCTechLists = new String[][]{new String[]{NfcF.class.getName()}};

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
            //방금 받은 파일명의 mp3가 있으면 플레이 시키자. 맞나 여기서 하는거?
            //아닌가 파일을 만들고 바로 실행되게 해야 하나? AsyncTask 백그라운드 작업중에...?

        }
    }

    public void onResume() {
        super.onResume();
        // 앱이 실행될때 NFC 어댑터를 활성화 한다
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);

        // NFC 태그 스캔으로 앱이 자동 실행되었을때
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()))
            // 인텐트에 포함된 정보를 분석해서 화면에 표시
            onNewIntent(getIntent());
    }

    public void onPause() {
        super.onPause();
        // 앱이 종료될때 NFC 어댑터를 비활성화 한다
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    // NFC 태그 정보 수신 함수. 인텐트에 포함된 정보를 분석해서 화면에 표시
    @Override
    public void onNewIntent(Intent intent) {
        // 인텐트에서 액션을 추출
        String action = intent.getAction();
        // 인텐트에서 태그 정보 추출
        String tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG).toString();
        String strMsg = action + "\n\n" + tag;
        // 액션 정보와 태그 정보를 화면에 출력


        //@@@@ mTextView.setText(strMsg);


        // 인텐트에서 NDEF 메시지 배열을 구한다
        Parcelable[] messages = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (messages == null) return;

        for (int i = 0; i < messages.length; i++)
            // NDEF 메시지를 화면에 출력
            showMsg((NdefMessage) messages[i]);
    }

    // NDEF 메시지를 화면에 출력
    public void showMsg(NdefMessage mMessage) {
        String strMsg = "", strRec = "";
        // NDEF 메시지에서 NDEF 레코드 배열을 구한다
        NdefRecord[] recs = mMessage.getRecords();
        for (int i = 0; i < recs.length; i++) {
            // 개별 레코드 데이터를 구한다
            NdefRecord record = recs[i];
            byte[] payload = record.getPayload();
            // 레코드 데이터 종류가 텍스트 일때
            if (Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                // 버퍼 데이터를 인코딩 변환
                strRec = byteDecoding(payload);
                strRec = strRec;
            }
            // 레코드 데이터 종류가 URI 일때

           /* else if( Arrays.equals(record.getType(), NdefRecord.RTD_URI) ) {
                strRec = new String(payload, 0, payload.length);
                strRec = "URI: " + strRec;
            }*/

            strMsg += (strRec);

        }

        //mTextView.append(strMsg);


        switch (strMsg){
            case "음료" :
                rfid = "해당 코너는 음료코너 입니다.";
                mTextString = new String[]{rfid};
                mNaverTTSTask = new NaverTTSTask();
                mNaverTTSTask.execute(mTextString);
                break;
            case "과자" :
                rfid = "해당 코너는 과자류코너 입니다.";
                mTextString = new String[]{rfid};
                mNaverTTSTask = new NaverTTSTask();
                mNaverTTSTask.execute(mTextString);
                break;
            case "도시락" :
                rfid = "해당 코너는 프레쉬 푸드코너 입니다.";
                mTextString = new String[]{rfid};
                mNaverTTSTask = new NaverTTSTask();
                mNaverTTSTask.execute(mTextString);
                break;
            case "라면" :
                rfid = "해당 코너는 라면코너 입니다.";
                mTextString = new String[]{rfid};
                mNaverTTSTask = new NaverTTSTask();
                mNaverTTSTask.execute(mTextString);
                break;
        }
        mTextString = new String[]{rfid};
        mNaverTTSTask = new NaverTTSTask();
        mNaverTTSTask.execute(mTextString);
    }

    // 버퍼 데이터를 디코딩해서 String 으로 변환
    public String byteDecoding(byte[] buf) {
        String strText = "";
        String textEncoding = ((buf[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
        int langCodeLen = buf[0] & 0077;

        try {
            strText = new String(buf, langCodeLen + 1,
                    buf.length - langCodeLen - 1, textEncoding);
        } catch (Exception e) {
            Log.d("tag1", e.toString());
        }
        return strText;
    }



}
