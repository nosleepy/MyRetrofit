package com.grandstream.myretrofit;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public class MyRetrofit {
    private final String TAG = "MyRetrofitTest";
    private final String baseUrl;

    private MyRetrofit(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static final class Builder {
        private String baseUrl;

        public Builder() {}

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public MyRetrofit build() {
            return new MyRetrofit(this.baseUrl);
        }
    }

    public <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return new OkHttpCall(parseMethod(method, args));
            }
        });
    }

    //拼接请求地址
    private String parseMethod(Method method, Object[] args) {

        Type genericReturnType = method.getGenericReturnType();
        Type returnType = method.getReturnType();
        Log.e(TAG, "genericReturnType = " + genericReturnType);
        Log.e(TAG, "returnType = " + returnType);

        if (genericReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
                Log.e(TAG, "typeArgument = " + typeArgument);
            }
        }

        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (Type type : genericParameterTypes) {
            Log.e(TAG, "type = " + type);
        }

        StringBuilder requestUrl = new StringBuilder(baseUrl);
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Get) {
                Get get = (Get) annotation;
                String value = get.value();
                requestUrl.append(value);
            }
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        int index = -1;
        for (Annotation[] parameters : parameterAnnotations) {
            index++;
            for (Annotation annotation : parameters) {
                if (annotation instanceof Query) {
                    Query queue = (Query) annotation;
                    String value = queue.value();
                    if ("id".equals(value)) {
                        requestUrl.append("/").append(args[index]);
                    }
                }
            }
        }
        Log.d(TAG, "requestUrl = " + requestUrl);
        return requestUrl.toString();
    }
}
