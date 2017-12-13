package com.example.yongjaenam.surewhynot;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// 네이버 음성합성 Open API 예제
//아주 아주 아주 중요!!!! 네트워크 접속을 사용시 AsyncTask(즉 쓰레드)를 사용해야 한다. 아 미쳐~

public class APIExamTTS {

    private static String TAG = "APIExamTTS";

    public static void main(String[] args, File adress) {
        String clientId = "BbPf0f_eKv3pkZRTJNNS";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "ibRSw90WKs";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(args[0], "UTF-8"); // 13자
            String apiURL = "https://openapi.naver.com/v1/voice/tts.bin";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "speaker=jinho&speed=2&text=" + text;
            con.setDoOutput(true);
            con.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());///여기서 에러 난다?
            Log.d(TAG, String.valueOf(wr));
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];

                File dir = new File(adress +"/", "Naver");
                if(!dir.exists()){
                    dir.mkdirs();
                }
                // 랜덤한 이름으로 mp3 파일 생성
                //String tempname = Long.valueOf(new Date().getTime()).toString();
                String tempname = "naverttstemp"; //하나의 파일명으로 덮어쓰기 하자.
                File f = new File(adress + File.separator + "Naver/" + tempname + ".mp3");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();

                String Path_to_file = adress+File.separator+"Naver/"+tempname+".mp3";
                MediaPlayer audioPlay = new MediaPlayer();
                audioPlay.setDataSource(Path_to_file);
                audioPlay.prepare();//이걸 해줘야 하는군. 없으면 에러난다.
                audioPlay.start();




            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

