package com.example.interview.fragment;

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
import com.example.interview.activity.DetailActivity;
import com.example.interview.adapter.SearchListAdapter;
import com.example.interview.adapter.OnItemResponseListener;
import com.example.interview.api.OnFetchCompleteListener;
import com.example.interview.api.SearchClient;
import com.example.interview.constant.Constant;
import com.example.interview.model.SearchResult;
import com.example.interview.item.SearchItemModel;
import com.example.interview.item.util.SearchItemModelUtils;

public class SearchListFragment extends Fragment
    implements
    OnFetchCompleteListener<SearchResult>,
    OnItemResponseListener,
    View.OnClickListener {

  private static final String TAG = "SearchListFragment";

  private SearchListAdapter mAdapter;
  private EditText mSearchTextField;
  private LinearLayoutManager mLayoutManager;
  private SearchClient mSearchClient;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setRetainInstance(true);

    if (savedInstanceState == null) {
      mSearchClient = new SearchClient(this);
      mAdapter = new SearchListAdapter(this);
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
    if (!key.isEmpty() && !mSearchClient.isCurrentKey(key)) {
      mAdapter.getData().clear();
      mSearchClient.search(key);
    }
  }

  @Override
  public void onSuccess(SearchResult response) {
    SearchItemModelUtils.updateDataSet(response, mAdapter.getData());
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void onFailure(Throwable t) {
    Log.v(TAG, "Loading failed");
  }

  @Override
  public void onResponse(View view, int adapterPosition) {

    SearchItemModel model = mAdapter.getData().get(adapterPosition);

    Bundle bundle = new Bundle();
    bundle.putString(Constant.sPhotoUrl, model.getPhotoUrl());
    bundle.putString(Constant.sName, model.getName());
    bundle.putString(Constant.sId, model.getId());
    bundle.putString(Constant.sType, model.getType().name());

    Intent intent = new Intent();
    intent.putExtras(bundle);
    intent.setClass(getActivity(), DetailActivity.class);
    getActivity().startActivity(intent);
  }
}
