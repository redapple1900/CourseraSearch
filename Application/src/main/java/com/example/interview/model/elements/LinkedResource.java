package com.example.interview.model.elements;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LinkedResource {

  @SerializedName("courses.v1")
  List<Course> mCourseList;

  @SerializedName("partners.v1")
  List<Partner> mPartnerList;

  @SerializedName("onDemandSpecializations.v1")
  List<Specialization> mSpecializationList;

  public List<Course> getCourseList() {
    return mCourseList;
  }

  public LinkedResource setCourseList(List<Course> courseList) {
    mCourseList = courseList;
    return this;
  }

  public List<Partner> getPartnerList() {
    return mPartnerList;
  }

  public LinkedResource setPartnerList(List<Partner> partnerList) {
    mPartnerList = partnerList;
    return this;
  }

  public List<Specialization> getSpecializationList() {
    return mSpecializationList;
  }

  public LinkedResource setSpecializationList(List<Specialization> specializationList) {
    mSpecializationList = specializationList;
    return this;
  }

  public class Course {

    @SerializedName("courseType")
    private String mType;

    @SerializedName("photoUrl")
    private String mPhotoUrl;

    @SerializedName("partnerIds")
    private List<Integer> mPartnerIds;

    @SerializedName("name")
    private String mName;

    @SerializedName("id")
    private String mId;

    @SerializedName("slug")
    private String mSlug;

    public String getSlug() {
      return mSlug;
    }

    public Course setSlug(String slug) {
      mSlug = slug;
      return this;
    }

    public String getType() {
      return mType;
    }

    public Course setType(String type) {
      mType = type;
      return this;
    }

    public String getPhotoUrl() {
      return mPhotoUrl;
    }

    public Course setPhotoUrl(String photoUrl) {
      mPhotoUrl = photoUrl;
      return this;
    }

    public List<Integer> getPartnerIds() {
      return mPartnerIds;
    }

    public Course setPartnerIds(List<Integer> partnerIds) {
      mPartnerIds = partnerIds;
      return this;
    }

    public String getName() {
      return mName;
    }

    public Course setName(String name) {
      mName = name;
      return this;
    }

    public String getId() {
      return mId;
    }

    public Course setId(String id) {
      mId = id;
      return this;
    }
  }

  public class Partner {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("shortName")
    private String mShortName;

    public int getId() {
      return mId;
    }

    public Partner setId(int id) {
      mId = id;
      return this;
    }

    public String getName() {
      return mName;
    }

    public Partner setName(String name) {
      mName = name;
      return this;
    }

    public String getShortName() {
      return mShortName;
    }

    public Partner setShortName(String shortName) {
      mShortName = shortName;
      return this;
    }
  }

  public class Specialization {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("tagline")
    private String mTagline;

    @SerializedName("logo")
    private String mLogo;

    @SerializedName("slug")
    private String mSlug;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("partnerIds")
    private List<Integer> mPartnerIds;

    @SerializedName("courseIds")
    private List<String> mCourseIds;

    public String getId() {
      return mId;
    }

    public Specialization setId(String id) {
      mId = id;
      return this;
    }

    public List<String> getCourseIds() {
      return mCourseIds;
    }

    public Specialization setCourseIds(List<String> courseIds) {
      mCourseIds = courseIds;
      return this;
    }

    public String getName() {
      return mName;
    }

    public Specialization setName(String name) {
      mName = name;
      return this;
    }

    public String getTagline() {
      return mTagline;
    }

    public Specialization setTagline(String tagline) {
      mTagline = tagline;
      return this;
    }

    public String getSlug() {
      return mSlug;
    }

    public Specialization setSlug(String slug) {
      mSlug = slug;
      return this;
    }

    public String getDescription() {
      return mDescription;
    }

    public Specialization setDescription(String description) {
      mDescription = description;
      return this;
    }

    public List<Integer> getPartnerIds() {
      return mPartnerIds;
    }

    public Specialization setPartnerIds(List<Integer> partnerIds) {
      mPartnerIds = partnerIds;
      return this;
    }

    public String getLogo() {
      return mLogo;
    }

    public Specialization setLogo(String logo) {
      mLogo = logo;
      return this;
    }
  }
}
