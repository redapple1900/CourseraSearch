package com.example.interview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.interview.R;
import com.example.interview.item.SearchItemModel;
import com.example.interview.item.SearchItemModel.Type;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

  private List<SearchItemModel> mDataSet;
  private OnItemResponseListener mOnItemResponseListener;

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;
    private final TextView nameView;
    private final TextView univNameView;
    private final TextView courseAmountView;

    public ViewHolder(View v) {
      super(v);
      imageView = (ImageView) v.findViewById(R.id.image);
      nameView = (TextView) v.findViewById(R.id.name);
      univNameView = (TextView) v.findViewById(R.id.university_name);
      courseAmountView = (TextView) v.findViewById(R.id.course_amount);
    }
  }

  public SearchListAdapter(OnItemResponseListener listener) {
    mDataSet = new ArrayList<>();
    mOnItemResponseListener = listener;
  }

  // The content of this list might be modified
  public List<SearchItemModel> getData() {
    return mDataSet;
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
    holder.univNameView.setText(getUnivName(model.getUnivNameList()));

    if (model.getType() == Type.COURSE) {
      holder.courseAmountView.setVisibility(View.GONE);
    } else if (model.getType() == Type.SPECIALIZATION) {
      holder.courseAmountView.setVisibility(View.VISIBLE);
      holder.courseAmountView.setText(
          holder.courseAmountView.getContext().getString(
              R.string.course_amount, model.getCourseAmount()));
    }

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

  /**
   * @param list List of University Names
   * @return A single string with all university names, each university has its own line.
   */
  private String getUnivName(List<String> list) {
    StringBuilder builder = new StringBuilder();

    for (int i = 0, j = list.size() - 1; i < j; i++) {
      builder.append(list.get(i).trim()).append("\n");
    }
    builder.append(list.get(list.size() - 1).trim());
    return builder.toString();
  }
}
