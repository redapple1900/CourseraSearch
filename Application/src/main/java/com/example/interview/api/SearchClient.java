package com.example.interview.api;

import android.util.Log;

import com.example.interview.item.util.SearchItemModelUtils;
import com.example.interview.model.SearchResult;
import com.example.interview.model.elements.PageInfo;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class SearchClient implements Callback<SearchResult> {

  private OnFetchCompleteListener<SearchResult> mOnFetchCompleteListener;

  private Map<String, String> mQueryMap;
  private Call<SearchResult> mSearchResultCall;

  private String mCurrentKey;
  private int mCurrentCursor;
  private PageInfo mPageInfo;

  public SearchClient(OnFetchCompleteListener<SearchResult> listener) {
    mOnFetchCompleteListener = listener;

    mQueryMap = new HashMap<>();
    mQueryMap.put("includes", "courseId,onDemandSpecializationId,courses.v1(partnerIds)");
    mQueryMap.put("fields", "courseId,onDemandSpecializationId," +
        "courses.v1(name,photoUrl,partnerIds),onDemandSpecializations.v1" +
        "(name,logo,courseIds,partnerIds),partners.v1(name)");
    mQueryMap.put("limit", "10");
    mQueryMap.put("q", "search");

    mCurrentKey = "";
    mCurrentCursor = 0;

    mPageInfo = new PageInfo();
  }

  public void search(String key) {
    if (key == null || key.isEmpty() || key.equals(mCurrentKey)) return;

    mCurrentKey = key;
    mCurrentCursor = 0;
    mPageInfo.setNext(0);
    mQueryMap.put("query", key);

    loadMore();
  }

  public void loadMore() {
    if (mPageInfo.getNext() != 0 && mPageInfo.getNext() >= mPageInfo.getTotal()) return;
    if (mCurrentCursor != 0 && mPageInfo.getNext() <= mCurrentCursor) return;

    mQueryMap.put("start", String.valueOf(mPageInfo.getNext()));
    mCurrentCursor = mPageInfo.getNext();

    mSearchResultCall = Clients.getClient().create(CourseSearchService.class).getResponse(mQueryMap);

    mSearchResultCall.enqueue(this);

    Log.d(getClass().getSimpleName(), mCurrentKey + " " + mPageInfo.getNext());
  }

  public void cancel() {
    if (mSearchResultCall != null &&
        !mSearchResultCall.isExecuted() &&
        !mSearchResultCall.isCanceled()) {
      mSearchResultCall.cancel();
    }
  }

  public boolean isCurrentKey(String key) {
    return mCurrentKey.equals(key);
  }

  @Override
  public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
    SearchItemModelUtils.updatePageInfo(response.body(), mPageInfo);
    mOnFetchCompleteListener.onResponse(response.body());
  }

  @Override
  public void onFailure(Call<SearchResult> call, Throwable t) {
    mOnFetchCompleteListener.onFailure(t);
  }

  interface CourseSearchService {

    @GET("api/catalogResults.v2")
    Call<SearchResult> getResponse(@QueryMap Map<String, String> map);
  }
}
