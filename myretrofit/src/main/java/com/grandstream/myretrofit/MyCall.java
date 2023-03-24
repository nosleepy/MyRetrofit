package com.grandstream.myretrofit;

public interface MyCall<T> {
    void enqueue(MyCallback<T> myCallback);
}
