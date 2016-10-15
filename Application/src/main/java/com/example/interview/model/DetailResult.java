package com.example.interview.model;

import com.google.gson.annotations.SerializedName;

import com.example.interview.model.elements.DetailElement;

import java.util.List;

public class DetailResult {

  @SerializedName("elements")
  private List<DetailElement> mDetailElementList;

  public List<DetailElement> getDetailElementList() {
    return mDetailElementList;
  }

  public DetailResult setDetailElementList(List<DetailElement> detailElementList) {
    mDetailElementList = detailElementList;
    return this;
  }
}
