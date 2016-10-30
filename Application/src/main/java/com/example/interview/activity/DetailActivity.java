package com.example.interview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.interview.R;
import com.example.interview.constant.Constant;
import com.example.interview.fragment.DetailFragment;

public class DetailActivity extends FragmentActivity {

  // View name of the header image. Used for activity scene transitions
  public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

  // View name of the header title. Used for activity scene transitions
  public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (getIntent().getExtras() == null ||
        getIntent().getExtras().getString(Constant.sType) == null) {
      finish();
    }

    if (savedInstanceState == null) {
      DetailFragment fragment = new DetailFragment();

      fragment.setArguments(getIntent().getExtras());

      getSupportFragmentManager().beginTransaction()
          .replace(R.id.fragment_container, fragment).commit();
    }
  }
}
