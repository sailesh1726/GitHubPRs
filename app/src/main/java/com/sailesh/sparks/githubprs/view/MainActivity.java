package com.sailesh.sparks.githubprs.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.sailesh.sparks.githubprs.R;
import com.sailesh.sparks.githubprs.databinding.PRBinding;
import com.sailesh.sparks.githubprs.viewmodel.PullRequestViewModel;
import com.sailesh.sparks.networkmodule.RetrofitClient;
import com.sailesh.sparks.networkmodule.model.PullRequest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements PullRequestViewModel.PullRequestCallback {

    private PRBinding mPrBinding;
    private PullRequestViewModel pullRequestViewModel;
    private List<PullRequest> mPullRequests;
    private PRAdapter mPRAdapter;
    private LinearLayoutManager mLinearLayoutManger;
    private PullRequestViewModel.PullRequestCallback mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mCallback = this;
        pullRequestViewModel = new PullRequestViewModel(mCallback);

        mLinearLayoutManger = new LinearLayoutManager(this);

        mPrBinding.pullRequestRecycleView.setLayoutManager(mLinearLayoutManger);
        mPrBinding.pullRequestRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mPullRequests = new ArrayList<>();

        mPRAdapter = new PRAdapter(this,mPullRequests);
        mPrBinding.pullRequestRecycleView.setAdapter(mPRAdapter);
        pullRequestViewModel.getPRRequests();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RetrofitClient.getInstance().cancelAllRequests();
        mCallback = null;
    }

    @Override
    public void updatePRList(List<PullRequest> pullRequests) {
        mPullRequests.addAll(pullRequests);
        mPRAdapter.notifyDataSetChanged();
    }

    @Override
    public void error(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
