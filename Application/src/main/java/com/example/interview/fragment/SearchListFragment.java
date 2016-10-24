package com.example.interview.fragment;

import static com.example.interview.constant.Constant.sBufferSize;

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

import com.example.interview.R;
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
  private Button mSearchButton;
  private EditText mSearchTextField;
  private RecyclerView mRecyclerView;
  private SearchClient mSearchClient;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Make sure content are retained after screen rotation
    setRetainInstance(true);

    mSearchClient = new SearchClient(this);
    mAdapter = new SearchListAdapter(this);
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, 
      ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.recycler_view_frag, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mSearchTextField = (EditText) view.findViewById(R.id.search_text_field);

    mSearchButton = (Button) view.findViewById(R.id.search_action_button);
    mSearchButton.setOnClickListener(this);

    mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy <= 0)  return;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

        if (visibleItemCount + pastVisibleItems + sBufferSize >= totalItemCount) {
          mSearchClient.loadMore();
        }
      }
    });
  }

  /**
   * Dereference all instances which hold the reference to the activity to avoid memory leak
   */
  @Override
  public void onDestroyView() {
    mRecyclerView = null;
    mSearchButton = null;
    mSearchTextField = null;
    super.onDestroyView();
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
