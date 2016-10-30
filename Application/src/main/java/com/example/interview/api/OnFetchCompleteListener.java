package com.example.interview.api;

public interface OnFetchCompleteListener<T> {

  void onResponse(T t);

  void onFailure(Throwable t);
}

