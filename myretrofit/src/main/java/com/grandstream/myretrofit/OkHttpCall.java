package com.grandstream.myretrofit;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpCall<T> implements MyCall<T> {
    private T t;

    private String url;

    public OkHttpCall(String url) {
        this.url = url;
    }

    @Override
    public void enqueue(MyCallback<T> callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(OkHttpCall.this, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(OkHttpCall.this, new MyResponse<T>((T)response.body().string()));
            }
        });
    }
}
