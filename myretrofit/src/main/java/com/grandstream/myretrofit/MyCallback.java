package com.grandstream.myretrofit;

public interface MyCallback<T> {
    void onResponse(MyCall<T> call, MyResponse<T> response);
    void onFailure(MyCall<T> call, Throwable t);
}
