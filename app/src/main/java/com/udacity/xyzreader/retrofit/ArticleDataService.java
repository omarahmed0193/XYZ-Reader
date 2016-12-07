package com.udacity.xyzreader.retrofit;


import com.udacity.xyzreader.beans.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticleDataService {
    @GET ("data.json")
    Call<List<Article>> getArticles();
}
