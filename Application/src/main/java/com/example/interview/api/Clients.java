package com.example.interview.api;

import static com.example.interview.constant.Constant.sBaseUrl;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Clients {

  private static Retrofit sRetrofit;

  public static Retrofit getClient() {
    if (sRetrofit == null) {
      sRetrofit = new Retrofit.Builder()
          .baseUrl(sBaseUrl)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }

    return sRetrofit;
  }
}
