package com.grandstream.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.grandstream.myretrofit.MyCall;
import com.grandstream.myretrofit.MyCallback;
import com.grandstream.myretrofit.MyResponse;
import com.grandstream.myretrofit.MyRetrofit;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MyRetrofitTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserService userService = new MyRetrofit.Builder().baseUrl("https://getman.cn/mock/").build().create(UserService.class);
        userService.getUserById(1).enqueue(new MyCallback<String>() {
            @Override
            public void onResponse(MyCall<String> call, MyResponse<String> response) {
                Log.d(TAG, "my retrofit response = " + response.body());
            }

            @Override
            public void onFailure(MyCall<String> call, Throwable t) {
                Log.d(TAG, "my retrofit failure = " + t.getMessage());
            }
        });
    }
}