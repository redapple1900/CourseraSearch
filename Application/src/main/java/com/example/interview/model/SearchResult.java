package com.example.interview.model;

import com.google.gson.annotations.SerializedName;

import com.example.interview.model.elements.SearchElement;
import com.example.interview.model.elements.LinkedResource;
import com.example.interview.model.elements.PageInfo;

import java.util.List;

public class SearchResult {

  @SerializedName("elements")
  private List<SearchElement> mSearchElement;

  @SerializedName("paging")
  private PageInfo mPageInfo;

  @SerializedName("linked")
  private LinkedResource mLinkedResource;

  public LinkedResource getLinkedResource() {
    return mLinkedResource;
  }

  public SearchResult setLinkedResource(LinkedResource linkedResource) {
    mLinkedResource = linkedResource;
    return this;
  }

  public List<SearchElement> getSearchElement() {
    return mSearchElement;
  }

  public SearchResult setSearchElement(List<SearchElement> searchElement) {
    mSearchElement = searchElement;
    return this;
  }

  public PageInfo getPageInfo() {
    return mPageInfo;
  }

  public SearchResult setPageInfo(PageInfo pageInfo) {
    mPageInfo = pageInfo;
    return this;
  }
}
