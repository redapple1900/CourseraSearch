package com.example.interview.fragment;

import static com.example.interview.activity.DetailActivity.VIEW_NAME_HEADER_IMAGE;
import static com.example.interview.activity.DetailActivity.VIEW_NAME_HEADER_TITLE;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.interview.R;
import com.example.interview.api.CourseDetailClient;
import com.example.interview.api.OnFetchCompleteListener;
import com.example.interview.constant.Constant;
import com.example.interview.item.SearchItemModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailFragment extends Fragment implements OnFetchCompleteListener<String>{

  @BindView((R.id.image)) ImageView mImageView;
  @BindView(R.id.name) TextView mNameView;
  @BindView(R.id.description) TextView mDescriptionView;
  private Unbinder mUnbinder;

  private String mDescription = "";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Make sure content are retained after screen rotation
    setRetainInstance(true);

    CourseDetailClient courseDetailClient = new CourseDetailClient(this);

    if (getArguments() == null ||
        getArguments().getString(Constant.sType) == null) {
      getActivity().finish();
    }

    SearchItemModel.Type type =
        SearchItemModel.Type.valueOf(getArguments().getString(Constant.sType));
    String id = getArguments().getString(Constant.sId);

    if (type == SearchItemModel.Type.COURSE) {
      courseDetailClient.loadCourse(id);
    } else if (type == SearchItemModel.Type.SPECIALIZATION) {
      courseDetailClient.loadSpecialization(id);
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.search_detail_layout, container, false);
    mUnbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ViewCompat.setTransitionName(mImageView, VIEW_NAME_HEADER_IMAGE);
    ViewCompat.setTransitionName(mNameView, VIEW_NAME_HEADER_TITLE);

    String photoUrl = getArguments().getString(Constant.sPhotoUrl);
    String name = getArguments().getString(Constant.sName);

    mNameView.setText(name);
    Picasso.with(getActivity()).load(photoUrl).noFade().fit().centerInside().into(mImageView);
    mDescriptionView.setText(mDescription);
  }

  /**
   * Dereference all instances which hold the reference to the activity to avoid memory leak
   */
  @Override
  public void onDestroyView() {
    mUnbinder.unbind();
    super.onDestroyView();
  }

  @Override
  public void onDestroy() {
    Log.d(getClass().getSimpleName(), "onDestroy");
    super.onDestroy();
  }

  @Override
  public void onResponse(String description) {
    if (!mDescription.equals(description)) {
      mDescription = description;
      if (mDescriptionView != null) mDescriptionView.setText(mDescription);
    }
  }

  @Override
  public void onFailure(Throwable t) {
    Log.d(getClass().getSimpleName(), t.toString());
  }
}
