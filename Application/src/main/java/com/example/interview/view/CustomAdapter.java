package com.example.interview.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.recyclerview.R;
import com.example.interview.viewmodel.SearchItemModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

  private List<SearchItemModel> mDataSet;
  private OnItemResponseListener mOnItemResponseListener;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameView;
    private final TextView univNameView;
    private final ImageView imageView;

    public ViewHolder(View v) {
      super(v);
      nameView = (TextView) v.findViewById(R.id.name);
      univNameView = (TextView) v.findViewById(R.id.university_name);
      imageView = (ImageView) v.findViewById(R.id.image);
    }
  }

  public CustomAdapter(List<SearchItemModel> dataSet, OnItemResponseListener listener) {
    mDataSet = dataSet;
    mOnItemResponseListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

    return new ViewHolder(LayoutInflater
        .from(viewGroup.getContext())
        .inflate(R.layout.row_item, viewGroup, false));
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    SearchItemModel model = mDataSet.get(position);

    holder.nameView.setText(model.getName());
    StringBuilder builder = new StringBuilder();
    for (String univName : model.getUnivNameList()) {
      builder.append(univName.trim()).append("\n");
    }
    holder.univNameView.setText(builder.toString());

    Picasso.with(holder.imageView.getContext())
        .load(model.getPhotoUrl())
        .fit()
        .centerCrop()
        .into(holder.imageView);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mOnItemResponseListener.onResponse(v, holder.getAdapterPosition());
      }
    });
  }

  @Override
  public int getItemCount() {
    return mDataSet.size();
  }
}
