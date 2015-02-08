package com.tonilopezmr.sample.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author toni.
 */
public abstract class AbstracRecyclerViewHolder extends RecyclerView.ViewHolder {

    public AbstracRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
