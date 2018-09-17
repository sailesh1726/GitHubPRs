package com.sailesh.sparks.githubprs.viewmodel;

import android.databinding.BaseObservable;

import com.sailesh.sparks.networkmodule.APIClient;
import com.sailesh.sparks.networkmodule.RetrofitClient;
import com.sailesh.sparks.networkmodule.model.PullRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PullRequestViewModel extends BaseObservable {

    private APIClient mAPIClient;
    private PullRequestCallback pullRequestCallback;
    private static final String OWNER = "square";
    private static final String REPO = "picasso";

    public PullRequestViewModel(PullRequestCallback pullRequestCallback) {

        this.pullRequestCallback = pullRequestCallback;
    }

    public void getPRRequests() {
        mAPIClient = RetrofitClient.getInstance().getNetworkClient();
        mAPIClient.getPullRequests(OWNER, REPO).enqueue(mPRResponseCallBack);
    }

    private Callback<List<PullRequest>> mPRResponseCallBack = new Callback<List<PullRequest>>() {
        @Override
        public void onResponse(Call<List<PullRequest>> call, Response<List<PullRequest>> response) {
            if(response.isSuccessful()){
                pullRequestCallback.updatePRList(response.body());
            }else {
                pullRequestCallback.error("Response Failed");
            }

        }

        @Override
        public void onFailure(Call<List<PullRequest>> call, Throwable t) {
            pullRequestCallback.error(t.getMessage());
        }
    };

    public interface PullRequestCallback {
        void updatePRList(List<PullRequest> pullRequests);
        void error(String msg);
    }
}
