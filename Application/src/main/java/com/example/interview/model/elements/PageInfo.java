package com.example.interview.model.elements;

import com.google.gson.annotations.SerializedName;

public class PageInfo {

  @SerializedName("next")
  private int mNext;

  @SerializedName("total")
  private int mTotal;

  public int getNext() {
    return mNext < mTotal ? mNext : mTotal;
  }

  public PageInfo setNext(int next) {
    mNext = next;
    return this;
  }

  public int getTotal() {
    return mTotal;
  }

  public PageInfo setTotal(int total) {
    mTotal = total;
    return this;
  }
}
