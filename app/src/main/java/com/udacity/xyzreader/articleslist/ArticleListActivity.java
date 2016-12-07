package com.udacity.xyzreader.articleslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.udacity.xyzreader.FetchArticlesService;
import com.udacity.xyzreader.R;
import com.udacity.xyzreader.Util;
import com.udacity.xyzreader.beans.Article;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class ArticleListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.articles_recycler_view)
    RecyclerView mArticlesRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        mRealm = Realm.getDefaultInstance();

        mArticlesRecyclerView.setAdapter(new ArticlesAdapter(this, mRealm.where(Article.class).findAll()));
        mArticlesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        if (savedInstanceState == null) {
            mSwipeRefreshLayout.setRefreshing(true);
            startService(new Intent(this, FetchArticlesService.class));
        }

        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRealm != null) {
            if (!mRealm.isClosed()) {
                mRealm.close();
            }
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRealm != null) {
            mRealm = Realm.getDefaultInstance();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onRefresh() {
        startService(new Intent(this, FetchArticlesService.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFetchArticlesCompleted(Integer resultCode) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (resultCode == 1) {
            String message = "";
            if (!Util.isConnectionAvailable(this)) {
                message += getString(R.string.message_connection_not_available);
            } else {
                message += getString(R.string.message_generic_error);
            }
            Snackbar.make(mArticlesRecyclerView, message, Snackbar.LENGTH_SHORT).show();
        }
    }
}
