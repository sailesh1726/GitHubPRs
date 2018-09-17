package com.sailesh.sparks.githubprs.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sailesh.sparks.githubprs.R;

import java.util.List;

public class SplitScreenAdapter extends RecyclerView.Adapter {
    private static final int TWO_SCREEN = 0;
    private static final int SINGLE_SCREEN = 1;
    private final Context context;
    private final List<String[]> rowDataList;

    public SplitScreenAdapter(Context context, List<String[]> rowDataList) {

        this.context = context;
        this.rowDataList = rowDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TWO_SCREEN:
                View splitView = inflater.inflate(R.layout.pull_request_diff_row_split_screen, parent, false);
                return new SplitScreenViewHolder(context, splitView);
            default:
                View singleView = inflater.inflate(R.layout.pull_request_diff_row_single_screen, parent, false);
                return new SingleScreenViewHolder(singleView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TWO_SCREEN) {
            ((SplitScreenViewHolder) holder).setSplitRow(rowDataList.get(position));
        } else {
            ((SingleScreenViewHolder) holder).setSingleScreenText(rowDataList.get(position)[0]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (rowDataList.get(position).length == 2) {
            return TWO_SCREEN;
        } else {
            return SINGLE_SCREEN;
        }
    }

    @Override
    public int getItemCount() {
        return rowDataList.size();
    }

    private static class SplitScreenViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final TextView negativeTextView;
        private final TextView positiveTextView;

        public SplitScreenViewHolder(Context context, View splitView) {
            super(splitView);
            this.context = context;
            negativeTextView = itemView.findViewById(R.id.negativeTextView);
            positiveTextView = itemView.findViewById(R.id.positiveTextView);
        }

        public void setSplitRow(String[] array) {
            if (!array[0].equals("") && !array[1].equals("")) {

                negativeTextView.setText(array[0]);
                positiveTextView.setText(array[1]);

                negativeTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                positiveTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));

            } else if (array[0].equals("")) {

                positiveTextView.setText(array[1]);

                negativeTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                positiveTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));

            } else {

                negativeTextView.setText(array[0]);

                negativeTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                positiveTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
        }
    }

    private static class SingleScreenViewHolder extends RecyclerView.ViewHolder {
        private final TextView singleScreenTextView;

        public SingleScreenViewHolder(View singleView) {
            super(singleView);
            singleScreenTextView = singleView.findViewById(R.id.singleScreenTextView);
        }

        public void setSingleScreenText(String content) {
            singleScreenTextView.setText(content);
        }

    }
}
