package com.tonilopezmr.sample.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tonilopezmr.sample.R;
import com.tonilopezmr.sample.ui.viewmodel.SubjectViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * @author toni.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{

    private List<SubjectViewModel> items;
    private int itemLayout;
    private OnRecyclerViewItemClickListener clickListener;
    private OnRecyclerViewItemLongClickListener longClickListener;

    public MyRecyclerAdapter(int itemLayout) {
        this.itemLayout = itemLayout;
        this.items = new ArrayList<>();
    }

    public MyRecyclerAdapter(List<SubjectViewModel> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubjectViewModel item = items.get(position);
        holder.nameSubjectViewModel.setText(getStringRow(item));
        holder.itemView.setTag(item);
    }

    private String getStringRow(SubjectViewModel SubjectViewModel){
        return SubjectViewModel.getId() + " " + SubjectViewModel.getName();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(SubjectViewModel item) {
        int position = items.size();
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void add(List<SubjectViewModel> models) {
        this.items.addAll(models);
        notifyDataSetChanged();
    }

    public void remove(SubjectViewModel item) {
        int position = items.indexOf(item);
        items.remove(item);
        notifyItemRemoved(position);
    }

    public void setOnLongClickListener(OnRecyclerViewItemLongClickListener<SubjectViewModel> listener){
        this.longClickListener = listener;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<SubjectViewModel> listener){
        this.clickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (clickListener != null){
            SubjectViewModel tag = (SubjectViewModel)view.getTag();
            clickListener.onItemClick(view, tag);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (longClickListener != null){
            SubjectViewModel tag = (SubjectViewModel)view.getTag();
            longClickListener.onItemLongClick(view, tag);
        }
        return false;
    }

    public interface OnRecyclerViewItemLongClickListener<Model> {
        public void onItemLongClick(View view, Model subject);
    }

    public interface OnRecyclerViewItemClickListener<Model> {
        public void onItemClick(View view, Model subject);
    }

    public static class ViewHolder extends AbstracRecyclerViewHolder{

        @InjectView(R.id.nameSubjectTextView)
        TextView nameSubjectViewModel;

        public ViewHolder(final View itemView) {
            super(itemView);
        }
    }

}
