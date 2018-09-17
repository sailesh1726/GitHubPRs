package com.sailesh.sparks.githubprs.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sailesh.sparks.githubprs.R;
import com.sailesh.sparks.githubprs.util.Util;
import com.sailesh.sparks.networkmodule.model.PullRequest;

import java.util.Date;
import java.util.List;

public class PRAdapter extends RecyclerView.Adapter<PRAdapter.PRViewHolder> {
    private Context context;
    private List<PullRequest> pullRequests;

    public PRAdapter(Context context, List<PullRequest> pullRequests) {
        this.context = context;
        this.pullRequests = pullRequests;
    }

    @NonNull
    @Override
    public PRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pull_request_row_item, parent, false);
        return new PRViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PRViewHolder holder, int position) {
        PullRequest pullRequest = pullRequests.get(position);
        holder.setItem(pullRequest);

        if (pullRequest != null) {
            holder.setTitle(pullRequest.getTitle());
            holder.setCreatedDateTextView(pullRequest.getId(),pullRequest.getCreatedDate(),pullRequest.getUser().getLogin());

        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return pullRequests.size();
    }

    public static class PRViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public PullRequest item;

        private final TextView titleTextView;
        private final TextView cdTextView;

        public PRViewHolder(View view) {
            super(view);

            this.view = view;

            titleTextView = view.findViewById(R.id.title_textView);
            cdTextView = view.findViewById(R.id.created_date_textView);
        }

        public void setTitle(String title) {
            titleTextView.setText(title);
        }

        public void setCreatedDateTextView(long id, Date date, String dev) {
            cdTextView.setText("#"+id +" opened on "+Util.dateFormat.format(date)+" by "+dev);
        }

        public void setItem(PullRequest item) {
            this.item = item;
        }
    }
}
