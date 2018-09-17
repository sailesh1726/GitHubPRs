package com.sailesh.sparks.githubprs.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sailesh.sparks.githubprs.R;
import com.sailesh.sparks.githubprs.model.PullRequestDiffRow;

import java.util.List;

public class PRDiffAdapter extends RecyclerView.Adapter<PRDiffAdapter.DiffViewHolder> {
    private final Context context;
    private final List<PullRequestDiffRow> mPullRequestDiffRows;

    public PRDiffAdapter(Context context, List<PullRequestDiffRow> mPullRequestDiffRows) {
        this.context = context;
        this.mPullRequestDiffRows = mPullRequestDiffRows;
    }

    @NonNull
    @Override
    public DiffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pull_request_diff_row, parent, false);
        return new DiffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiffViewHolder holder, int position) {
        holder.setPullRequestDiffRow(mPullRequestDiffRows.get(position));
        holder.diffRowTitleTextView.setText(mPullRequestDiffRows.get(position).getRowTitle());

        holder.redGreenRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        SplitScreenAdapter splitScreenAdapter = new SplitScreenAdapter(context, mPullRequestDiffRows.get(position).getRowDataList());
        holder.redGreenRecyclerView.setAdapter(splitScreenAdapter);
    }

    @Override
    public int getItemCount() {
        return mPullRequestDiffRows.size();
    }

    static class DiffViewHolder extends RecyclerView.ViewHolder {

        TextView diffRowTitleTextView;
        RecyclerView redGreenRecyclerView;
        PullRequestDiffRow pullRequestDiffRow;

        DiffViewHolder(View view) {
            super(view);
            diffRowTitleTextView = view.findViewById(R.id.diff_row_title_textView);
            redGreenRecyclerView = view.findViewById(R.id.red_green_recycle_view);
        }

        public void setPullRequestDiffRow(PullRequestDiffRow pullRequestDiffRow) {
            this.pullRequestDiffRow = pullRequestDiffRow;
        }
    }
}
