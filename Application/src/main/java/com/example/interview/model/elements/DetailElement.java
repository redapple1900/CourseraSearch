package com.example.interview.model.elements;

import com.google.gson.annotations.SerializedName;

public class DetailElement {

  @SerializedName("id")
  private String mId;

  @SerializedName("name")
  private String mName;

  @SerializedName("slug")
  private String mSlug;

  @SerializedName("description")
  private String mDescription;

  @SerializedName("courseType")
  private String mCourseType;

  public String getId() {
    return mId;
  }

  public DetailElement setId(String id) {
    mId = id;
    return this;
  }

  public String getName() {
    return mName;
  }

  public DetailElement setName(String name) {
    mName = name;
    return this;
  }

  public String getSlug() {
    return mSlug;
  }

  public DetailElement setSlug(String slug) {
    mSlug = slug;
    return this;
  }

  public String getDescription() {
    return mDescription;
  }

  public DetailElement setDescription(String description) {
    mDescription = description;
    return this;
  }

  public String getCourseType() {
    return mCourseType;
  }

  public DetailElement setCourseType(String courseType) {
    mCourseType = courseType;
    return this;
  }
}
