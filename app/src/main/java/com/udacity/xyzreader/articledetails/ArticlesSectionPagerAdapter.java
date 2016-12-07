package com.udacity.xyzreader.articledetails;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.udacity.xyzreader.beans.Article;

import io.realm.RealmResults;

public class ArticlesSectionPagerAdapter extends FragmentStatePagerAdapter {


    private RealmResults<Article> mArticles;

    public ArticlesSectionPagerAdapter(FragmentManager fm, RealmResults<Article> articles) {
        super(fm);
        mArticles = articles;
    }

    @Override
    public Fragment getItem(int position) {
        return ArticleDetailsFragment.newInstance(mArticles.get(position));
    }

    @Override
    public int getCount() {
        return mArticles.size();
    }
}
