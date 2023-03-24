package com.grandstream.myapplication;

import com.grandstream.myretrofit.Get;
import com.grandstream.myretrofit.MyCall;
import com.grandstream.myretrofit.Query;

public interface UserService {
    @Get("users")
    MyCall<String> getUserById(@Query("id") int id);
}
