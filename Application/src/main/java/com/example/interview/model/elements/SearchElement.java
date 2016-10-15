package com.example.interview.model.elements;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchElement {

  @SerializedName("id")
  private String mId;

  @SerializedName("entries")
  private List<Entry> mEntryList;

  public String getId() {
    return mId;
  }

  public SearchElement setId(String id) {
    mId = id;
    return this;
  }

  public List<Entry> getEntryList() {
    return mEntryList;
  }

  public SearchElement setEntryList(List<Entry> entryList) {
    mEntryList = entryList;
    return this;
  }
}
