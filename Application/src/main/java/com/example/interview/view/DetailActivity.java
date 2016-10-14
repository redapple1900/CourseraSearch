package com.example.interview.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.recyclerview.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_detail_layout);

    String photoUrl = getIntent().getExtras().getString("PhotoUrl");
    String name = getIntent().getExtras().getString("name");

    ImageView imageView = (ImageView) findViewById(R.id.image);

    Picasso.with(this).load(photoUrl).fit().centerCrop().into(imageView);

    TextView nameView = (TextView) findViewById(R.id.name);
    nameView.setText(name);
  }
}
