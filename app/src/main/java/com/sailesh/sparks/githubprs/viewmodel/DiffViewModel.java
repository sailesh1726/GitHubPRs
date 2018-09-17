package com.sailesh.sparks.githubprs.viewmodel;

import com.sailesh.sparks.githubprs.model.PullRequestDiffRow;
import com.sailesh.sparks.githubprs.util.Util;
import com.sailesh.sparks.networkmodule.APIClient;
import com.sailesh.sparks.networkmodule.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiffViewModel {
    private DiffViewModelCallBack diffViewModelCallBack;
    private APIClient mAPIClient;

    public DiffViewModel(DiffViewModelCallBack diffViewModelCallBack) {

        this.diffViewModelCallBack = diffViewModelCallBack;
    }

    public void getDiff(String diffUrl) {
        mAPIClient = RetrofitClient.getInstance().getNetworkClient();
        mAPIClient.getDiff(diffUrl).enqueue(mDiffCallBack);
    }

    private Callback<ResponseBody> mDiffCallBack = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            diffViewModelCallBack.updateList(Util.parsePRDiffResponse(response.body()));
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            diffViewModelCallBack.error(t.getMessage());
        }
    };

    public interface DiffViewModelCallBack {
        void updateList(List<PullRequestDiffRow> pullRequestDiffRows);

        void error(String msg);
    }
}
