package com.udacity.xyzreader;

import android.app.IntentService;
import android.content.Intent;

import com.udacity.xyzreader.beans.Article;
import com.udacity.xyzreader.retrofit.ArticleDataService;
import com.udacity.xyzreader.retrofit.ServiceGenerator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FetchArticlesService extends IntentService {

    private Realm mRealm;

    public FetchArticlesService() {
        super("FetchArticlesService");
    }

    public FetchArticlesService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArticleDataService articleDataService = ServiceGenerator.createService(ArticleDataService.class);
        articleDataService.getArticles()
                .enqueue(new Callback<List<Article>>() {
                    @Override
                    public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                        if (response.isSuccessful()) {
                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(response.body());
                            mRealm.commitTransaction();
                            EventBus.getDefault().post(0);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Article>> call, Throwable t) {
                        EventBus.getDefault().post(1);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            if (!mRealm.isClosed()) {
                mRealm.close();
            }
        }
    }
}
