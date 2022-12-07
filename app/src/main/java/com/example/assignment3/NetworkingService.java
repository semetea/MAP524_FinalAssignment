package com.example.assignment3;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {
    String accessIdURL = "https://api.nexon.co.kr/fifaonline4/v1.0/users?nickname=";
    String rankURL = "https://api.nexon.co.kr/fifaonline4/v1.0/users/";
    String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJYLUFwcC1SYXRlLUxpbWl0IjoiNTAwOjEwIiwiYWNjb3VudF9pZCI6IjE2NzgwNDcwMjUiLCJhdXRoX2lkIjoiMiIsImV4cCI6MTY4NTM3MTA4MywiaWF0IjoxNjY5ODE5MDgzLCJuYmYiOjE2Njk4MTkwODMsInNlcnZpY2VfaWQiOiI0MzAwMTE0ODEiLCJ0b2tlbl90eXBlIjoiQWNjZXNzVG9rZW4ifQ.fx6SOCm2mWMlqI4zVjnQiG7LHOx0aamU7-9aXVz-Vqc";

    interface NetworkingListener {
        void connectionISDoneWithResult(JSONObject json) throws JSONException;
        void connectionIsDoneWithRank(String json);
    }

    public NetworkingListener listener;

    ExecutorService networkingExecutorService = Executors.newFixedThreadPool(4);
    Handler newtworkingHandler = new Handler(Looper.getMainLooper());

    public void getAccessId(String nickname) {
        String url = accessIdURL + nickname;
        connect(url);
    }
    public void getRank(String accessId) {
        String urlString = rankURL + accessId + "/maxdivision";
        networkingExecutorService.execute(new Runnable() {
            HttpURLConnection urlConnection;
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    urlConnection  = (HttpURLConnection)url.openConnection();
                    urlConnection.setRequestProperty("Authorization", API_KEY);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int value = 0;
                    String jsonString = "";
                    while ( (value = reader.read()) != -1 ){
                        char current = (char)value;
                        jsonString+= current;
                    }

                    final String json = jsonString;

                    newtworkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.connectionIsDoneWithRank(json);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    urlConnection.disconnect();
                }
            }
        });
    }

    public void connect(String urlstring){// general function for all connection
        networkingExecutorService.execute(new Runnable() {
            HttpURLConnection urlConnection;
            @Override
            public void run() {
                // run in background thread
                try {
                    URL url = new URL(urlstring);
                    urlConnection  = (HttpURLConnection)url.openConnection();
                    urlConnection.setRequestProperty("Authorization", API_KEY);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json");


                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
                    StringBuilder sb = new StringBuilder();
                    int cp;
                    while ((cp = reader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    String jsonText = sb.toString();
                    JSONObject json = new JSONObject(jsonText);


                    // we need to comeback to main thread when done.
                    newtworkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                listener.connectionISDoneWithResult(json);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }


            }
        });


    }
}
