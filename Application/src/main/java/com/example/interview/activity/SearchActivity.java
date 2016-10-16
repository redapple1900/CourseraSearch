package com.example.interview.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.interview.R;
import com.example.interview.fragment.SearchListFragment;

public class SearchActivity extends FragmentActivity {

  private static final String sTag = SearchListFragment.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

      SearchListFragment fragment;
      if (getSupportFragmentManager().findFragmentByTag(sTag) != null) {
        fragment = (SearchListFragment) getSupportFragmentManager().findFragmentByTag(sTag);
        transaction.replace(R.id.fragment_container, fragment, sTag).commit();
      } else {
        fragment = new SearchListFragment();
        transaction.add(R.id.fragment_container, fragment, sTag).commit();
      }
    }
  }
}
