package com.example.interview.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.recyclerview.R;
import com.example.interview.data.OnFetchCompleteListener;
import com.example.interview.data.SearchClient;
import com.example.interview.viewmodel.SearchItemModel;

public class SearchListFragment extends Fragment implements
    OnFetchCompleteListener,
    OnItemResponseListener,
    View.OnClickListener {

  private static final String TAG = "SearchListFragment";

  private EditText mSearchTextField;
  private LinearLayoutManager mLayoutManager;
  
  private SearchClient mSearchClient;
  private CustomAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setRetainInstance(true);

    if (savedInstanceState == null) {
      mSearchClient = new SearchClient(this);
      mAdapter = new CustomAdapter(mSearchClient.getData(), this);
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, 
      ViewGroup container,
      Bundle savedInstanceState) {
    
    View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
    rootView.setTag(TAG);

    mSearchTextField = (EditText) rootView.findViewById(R.id.search_text_field);

    mLayoutManager = new LinearLayoutManager(getActivity());

    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setAdapter(mAdapter);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy <= 0)  return;

        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
          mSearchClient.loadMore();
        }
      }
    });

    Button searchButton = (Button) rootView.findViewById(R.id.search_action_button);
    searchButton.setOnClickListener(this);

    return rootView;
  }

  @Override
  public void onClick(View v) {
    String key = mSearchTextField.getText().toString().trim();
    if (!key.isEmpty()) {
      mSearchClient.search(key);
    }
  }

  @Override
  public void onSuccess() {
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void onFailure(Throwable t) {
    Log.v(TAG, "Loading failed");
  }

  @Override
  public void onResponse(View view, int adapterPosition) {

    SearchItemModel model = mSearchClient.getData().get(adapterPosition);

    Bundle bundle = new Bundle();
    bundle.putString("PhotoUrl", model.getPhotoUrl());
    bundle.putString("name", model.getName());
    bundle.putString("id", model.getId());

    Intent intent = new Intent();
    intent.putExtras(bundle);
    intent.setClass(getActivity(), DetailActivity.class);
    getActivity().startActivity(intent);
  }
}
