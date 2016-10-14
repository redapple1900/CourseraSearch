package com.example.interview.data;


import com.example.interview.model.CourseDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class CourseDetailClient implements Callback<CourseDetailResponse> {

  private static final String sBaseUrl = "https://api.coursera.org/";
  private static final String sCourseFields = "photoUrl,description";
  private static final String sSpecializationFields = "logo,description";

  private OnFetchCompleteListener mOnFetchCompleteListener;

  private CourseDetailService mCourseDetailService;
  private SpecializationDetailService mSpecializationDetailService;

  public CourseDetailClient(OnFetchCompleteListener onFetchCompleteListener) {
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

  @Override
  public void onResponse(Call<CourseDetailResponse> call, Response<CourseDetailResponse> response) {

  }

  @Override
  public void onFailure(Call<CourseDetailResponse> call, Throwable t) {

  }

  interface CourseDetailService {

    @GET("api/courses.v1/{courseId}")
    Call<CourseDetailResponse> getResponse(
        @Path("courseId") String courseId,
        @Query("fields") String fields);
  }

  interface SpecializationDetailService {

    @GET("api/onDemandSpecializations.v1/{specializationId}")
    Call<CourseDetailResponse> getResponse(
        @Path("specializationId") String specializationId,
        @Query("fields") String fields);
  }
}
