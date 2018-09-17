package com.sailesh.sparks.githubprs.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.sailesh.sparks.githubprs.R;
import com.sailesh.sparks.githubprs.adapters.PRDiffAdapter;
import com.sailesh.sparks.githubprs.databinding.DiffBinding;
import com.sailesh.sparks.githubprs.model.PullRequestDiffRow;
import com.sailesh.sparks.githubprs.util.Constants;
import com.sailesh.sparks.githubprs.viewmodel.DiffViewModel;
import com.sailesh.sparks.networkmodule.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class DiffActivity extends BaseActivity implements DiffViewModel.DiffViewModelCallBack {

    private DiffBinding mDiffBinding;
    private String title;
    private String diffUrl;
    private LinearLayoutManager mLinearLayoutManger;
    private PRDiffAdapter prDiffAdapter;
    private DiffViewModel diffViewModel;
    private List<PullRequestDiffRow> mPullRequestDiffRows;
    private DiffViewModel.DiffViewModelCallBack mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDiffBinding = DataBindingUtil.setContentView(this, R.layout.activity_diff);
        mCallback = this;
        diffViewModel = new DiffViewModel(mCallback);

        mPullRequestDiffRows = new ArrayList<>();
        prDiffAdapter = new PRDiffAdapter(this, mPullRequestDiffRows);

        if (getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString(Constants.TITLE);
            diffUrl = getIntent().getExtras().getString(Constants.DIFF_URL);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);

        mLinearLayoutManger = new LinearLayoutManager(this);

        mDiffBinding.diffRecycleView.setLayoutManager(mLinearLayoutManger);
        mDiffBinding.diffRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mDiffBinding.diffRecycleView.setAdapter(prDiffAdapter);

        diffViewModel.getDiff(diffUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitClient.getInstance().cancelAllRequests();
        mCallback = null;
    }

    @Override
    public void updateList(List<PullRequestDiffRow> pullRequestDiffRows) {
        mPullRequestDiffRows.addAll(pullRequestDiffRows);
        prDiffAdapter.notifyDataSetChanged();
    }

    @Override
    public void error(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
