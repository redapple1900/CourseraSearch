package com.example.interview.api;

public interface OnFetchCompleteListener<T> {

  void onSuccess(T t);

  void onFailure(Throwable t);
}

