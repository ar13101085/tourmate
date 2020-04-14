package com.example.tourmate.http;

import com.example.tourmate.infrustructure.ICallback;
import com.example.tourmate.model.KeyValue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

public class TourmateHttpClient {
    public static void Get(String url, ArrayList<KeyValue> keyValues, final ICallback callback){
        AsyncHttpClient client=new AsyncHttpClient();
        for (KeyValue keyValue : keyValues
             ) {
            client.addHeader(keyValue.keyName, keyValue.value);
        }

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                String res=new String(responseBody);
                callback.receivedData(1,true,res);
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    String res=new String(responseBody);
                    callback.receivedData(statusCode,true,res);
                } catch (Exception e) {
                    callback.receivedData(statusCode,true,"Something is wrong.try again...");
                    e.printStackTrace();
                }
            }
        });
    }
}
