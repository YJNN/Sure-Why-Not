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
    NfcAdapter mNfcAdapter; 
    PendingIntent mPendingIntent; 
    IntentFilter[] mIntentFilters; 
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

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            return;
        }
        Intent intent = new Intent(this, getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
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
            APIExamTTS.main(mTextString, getFilesDir());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    public void onResume() {
        super.onResume();
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()))
            onNewIntent(getIntent());
    }

    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }
    @Override
    public void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG).toString();
        String strMsg = action + "\n\n" + tag;
        Parcelable[] messages = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (messages == null) return;
        for (int i = 0; i < messages.length; i++)
            showMsg((NdefMessage) messages[i]);
    }
    public void showMsg(NdefMessage mMessage) {
        String strMsg = "", strRec = "";
        NdefRecord[] recs = mMessage.getRecords();
        for (int i = 0; i < recs.length; i++) {
            NdefRecord record = recs[i];
            byte[] payload = record.getPayload();
            if (Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                strRec = byteDecoding(payload);
                strRec = strRec;
            }
            strMsg += (strRec);
        }
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
