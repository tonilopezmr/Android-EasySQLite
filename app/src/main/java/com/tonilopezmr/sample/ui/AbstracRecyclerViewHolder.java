package com.tonilopezmr.sample.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by toni on 07/02/15.
 */
public abstract class AbstracRecyclerViewHolder extends RecyclerView.ViewHolder {

    public AbstracRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
