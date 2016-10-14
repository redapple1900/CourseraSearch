package com.example.interview.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.recyclerview.R;
import com.example.interview.data.OnFetchCompleteListener;
import com.squareup.picasso.Picasso;

public class SearchDetailFragment extends Fragment implements OnFetchCompleteListener {

  @Override
  public View onCreateView(
      LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.search_detail_layout, container, false);

    ImageView imageView = (ImageView) rootView.findViewById(R.id.image);

    Picasso.with(getContext())
        .load(getArguments().getString("PhotoUrl"))
        .fit()
        .into(imageView);

    TextView nameView = (TextView) rootView.findViewById(R.id.name);
    nameView.setText(getArguments().getString("Name"));

    return rootView;
  }
  @Override
  public void onSuccess() {

  }

  @Override
  public void onFailure(Throwable t) {

  }
}
