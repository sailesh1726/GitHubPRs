package com.sailesh.sparks.networkmodule;


import com.sailesh.sparks.networkmodule.model.PullRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIClient {
    @GET("repos/{owner}/{repo}/pulls")
    Call<List<PullRequest>> getPullRequests(
            @Path("owner") String repositoryOwner,
            @Path("repo") String repository
    );

    @GET
    Call<ResponseBody> getDiff(
            @Url String url
    );
}
