package com.example.interview.model;

import com.google.gson.annotations.SerializedName;

import com.example.interview.model.elements.Element;
import com.example.interview.model.elements.LinkedResource;
import com.example.interview.model.elements.PageInfo;

import java.util.List;

public class CourseSearchResponse {

  @SerializedName("elements")
  private List<Element> mElement;

  @SerializedName("paging")
  private PageInfo mPageInfo;

  @SerializedName("linked")
  private LinkedResource mLinkedResource;

  public LinkedResource getLinkedResource() {
    return mLinkedResource;
  }

  public CourseSearchResponse setLinkedResource(LinkedResource linkedResource) {
    mLinkedResource = linkedResource;
    return this;
  }

  public List<Element> getElement() {
    return mElement;
  }

  public CourseSearchResponse setElement(List<Element> element) {
    mElement = element;
    return this;
  }

  public PageInfo getPageInfo() {
    return mPageInfo;
  }

  public CourseSearchResponse setPageInfo(PageInfo pageInfo) {
    mPageInfo = pageInfo;
    return this;
  }
}
