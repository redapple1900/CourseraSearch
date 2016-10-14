package com.example.interview.viewmodel;

import java.util.List;

public class SearchItemModel {

    public enum Type {
      COURSE,
      SPECIALIZATION
    }

    private Type mType;

    private String mId;

    private String mName;

    private String mPhotoUrl;

    private List<String> mUnivNameList;

    private int mCourseAmount;

  public Type getType() {
    return mType;
  }

  public SearchItemModel setType(Type type) {
    mType = type;
    return this;
  }

  public String getId() {
    return mId;
  }

  public SearchItemModel setId(String id) {
    mId = id;
    return this;
  }

  public String getName() {
    return mName;
  }

  public SearchItemModel setName(String name) {
    mName = name;
    return this;
  }

  public String getPhotoUrl() {
    return mPhotoUrl;
  }

  public SearchItemModel setPhotoUrl(String photoUrl) {
    mPhotoUrl = photoUrl;
    return this;
  }

  public List<String> getUnivNameList() {
    return mUnivNameList;
  }

  public SearchItemModel setUnivNameList(List<String> univNameList) {
    mUnivNameList = univNameList;
    return this;
  }

  public int getCourseAmount() {
    return mCourseAmount;
  }

  public SearchItemModel setCourseAmount(int courseAmount) {
    mCourseAmount = courseAmount;
    return this;
  }
}
