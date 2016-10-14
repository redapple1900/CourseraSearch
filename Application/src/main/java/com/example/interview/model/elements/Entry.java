package com.example.interview.model.elements;

import com.google.gson.annotations.SerializedName;

public class Entry {

  public enum Type {
    @SerializedName("courses.v1") COURSE,
    @SerializedName("onDemandSpecializations.v1") SPECIALIZATION
  }

  @SerializedName("score")
  private double mScore;

  @SerializedName("resourceName")
  private Type mType;

  @SerializedName("id")
  private String mId;

  public double getScore() {
    return mScore;
  }

  public Entry setScore(double score) {
    mScore = score;
    return this;
  }

  public Type getType() {
    return mType;
  }

  public Entry setType(Type type) {
    mType = type;
    return this;
  }

  public String getId() {
    return mId;
  }

  public Entry setId(String id) {
    mId = id;
    return this;
  }
}
