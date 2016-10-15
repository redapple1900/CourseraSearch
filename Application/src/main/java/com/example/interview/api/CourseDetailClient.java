package com.example.interview.api;


import com.example.interview.model.DetailResult;
import com.example.interview.model.elements.DetailElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class CourseDetailClient implements Callback<DetailResult> {

  private static final String sBaseUrl = "https://api.coursera.org/";
  private static final String sCourseFields = "photoUrl,description";
  private static final String sSpecializationFields = "logo,description";

  private OnFetchCompleteListener<String> mOnFetchCompleteListener;

  private CourseDetailService mCourseDetailService;
  private SpecializationDetailService mSpecializationDetailService;

  public CourseDetailClient(OnFetchCompleteListener<String> onFetchCompleteListener) {
    mOnFetchCompleteListener = onFetchCompleteListener;

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(sBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    mCourseDetailService = retrofit.create(CourseDetailService.class);
    mSpecializationDetailService = retrofit.create(SpecializationDetailService.class);
  }

  public void loadCourse(String courseId) {
    mCourseDetailService.getResponse(courseId, sCourseFields).enqueue(this);
  }

  public void loadSpecialization(String specializationId) {
    mSpecializationDetailService.getResponse(specializationId, sSpecializationFields).enqueue(this);
  }

  @Override
  public void onResponse(Call<DetailResult> call, Response<DetailResult> response) {
    if (response.body() == null ||
        response.body().getDetailElementList()  == null ||
        response.body().getDetailElementList().isEmpty()) return;
    DetailElement detailElement = response.body().getDetailElementList().get(0);
    mOnFetchCompleteListener.onSuccess(detailElement.getDescription());
  }

  @Override
  public void onFailure(Call<DetailResult> call, Throwable t) {

  }

  interface CourseDetailService {

    @GET("api/courses.v1/{courseId}")
    Call<DetailResult> getResponse(
        @Path("courseId") String courseId,
        @Query("fields") String fields);
  }

  interface SpecializationDetailService {

    @GET("api/onDemandSpecializations.v1/{specializationId}")
    Call<DetailResult> getResponse(
        @Path("specializationId") String specializationId,
        @Query("fields") String fields);
  }
}
