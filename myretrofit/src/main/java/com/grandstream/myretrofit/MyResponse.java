package com.grandstream.myretrofit;

public class MyResponse<T> {
    private T body;

    public MyResponse(T body) {
        this.body = body;
    }

    public T body() {
        return body;
    }
}
