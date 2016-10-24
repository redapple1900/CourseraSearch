package com.example.interview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.interview.R;
import com.example.interview.api.CourseDetailClient;
import com.example.interview.api.OnFetchCompleteListener;
import com.example.interview.constant.Constant;
import com.example.interview.item.SearchItemModel.Type;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends Activity implements OnFetchCompleteListener<String> {

  private CourseDetailClient mCourseDetailClient = new CourseDetailClient(this);

  @BindView(R.id.description) TextView mDescriptionView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_detail_layout);
    ButterKnife.bind(this);

    if (getIntent().getExtras() == null ||
        getIntent().getExtras().getString(Constant.sType) == null) {
      finish();
    }

    Type type =
        Type.valueOf(getIntent().getExtras().getString(Constant.sType));
    String id = getIntent().getExtras().getString(Constant.sId);
    String photoUrl = getIntent().getExtras().getString(Constant.sPhotoUrl);
    String name = getIntent().getExtras().getString(Constant.sName);

    ImageView imageView = (ImageView) findViewById(R.id.image);
    Picasso.with(this).load(photoUrl).fit().centerInside().into(imageView);

    TextView nameView = (TextView) findViewById(R.id.name);
    nameView.setText(name);

    // mDescriptionView = (TextView) findViewById(R.id.description);

    if (type == Type.COURSE) {
      mCourseDetailClient.loadCourse(id);
    } else if (type == Type.SPECIALIZATION) {
      mCourseDetailClient.loadSpecialization(id);
    }
  }

  @Override
  public void onSuccess(String description) {
    mDescriptionView.setText(description);
  }

  @Override
  public void onFailure(Throwable t) {
    Log.d(getClass().getSimpleName(), t.toString());
  }
}
