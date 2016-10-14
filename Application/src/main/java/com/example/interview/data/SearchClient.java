package com.example.interview.data;

import com.example.interview.model.CourseSearchResponse;
import com.example.interview.model.elements.PageInfo;
import com.example.interview.viewmodel.SearchItemModel;
import com.example.interview.viewmodel.util.SearchItemModelUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class SearchClient implements Callback<CourseSearchResponse> {

  private static final String sBaseUrl = "https://api.coursera.org/";

  private OnFetchCompleteListener mOnFetchCompleteListener;

  private CourseraSearchService mSearchService;
  private Map<String, String> mQueryMap;

  private String mCurrentKey;
  private PageInfo mPageInfo;
  private List<SearchItemModel> mDataSet;

  public SearchClient(OnFetchCompleteListener listener) {
    mOnFetchCompleteListener = listener;

    mSearchService = new Retrofit.Builder()
        .baseUrl(sBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CourseraSearchService.class);

    mQueryMap = new HashMap<>();
    mQueryMap.put("includes", "courseId,onDemandSpecializationId,courses.v1(partnerIds)");
    mQueryMap.put("fields", "courseId,onDemandSpecializationId,courses.v1(name,photoUrl,partnerIds),onDemandSpecializations.v1(name,logo,courseIds,partnerIds),partners.v1(name)");
    mQueryMap.put("limit", "10");
    mQueryMap.put("q", "search");

    mCurrentKey = "";
    mPageInfo = new PageInfo();
    mDataSet = new ArrayList<>();
  }

  public void search(String key) {
    if (key == null || key.isEmpty() || key.equals(mCurrentKey)) return;

    mCurrentKey = key;
    mDataSet.clear();
    mQueryMap.put("query", key);

    loadMore();
  }

  public void loadMore() {
    mQueryMap.put("start", String.valueOf(mPageInfo.getNext()));

    mSearchService.getResponse(mQueryMap).enqueue(this);
  }

  public List<SearchItemModel> getData() {
    return mDataSet;
  }

  @Override
  public void onResponse(Call<CourseSearchResponse> call, Response<CourseSearchResponse> response) {
    SearchItemModelUtils.updatePageInfo(response.body(), mPageInfo);
    SearchItemModelUtils.updateDataSet(response.body(), mDataSet);
    mOnFetchCompleteListener.onSuccess();
  }

  @Override
  public void onFailure(Call<CourseSearchResponse> call, Throwable t) {
    mOnFetchCompleteListener.onFailure(t);
  }

  interface CourseraSearchService {

    @GET("api/catalogResults.v2")
    Call<CourseSearchResponse> getResponse(@QueryMap Map<String, String> map);
  }
}
