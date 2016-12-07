package com.udacity.xyzreader.articledetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.udacity.xyzreader.R;
import com.udacity.xyzreader.beans.Article;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class ArticleDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE_POSITION = "extra_article_position";
    @BindView(R.id.articles_view_pager)
    ViewPager mArticlesViewPager;

    private Realm mRealm;
    private RealmResults<Article> mArticleRealmResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        ButterKnife.bind(this);

        mRealm = Realm.getDefaultInstance();

        mArticleRealmResults = mRealm.where(Article.class).findAll();
        mArticlesViewPager.setAdapter(new ArticlesSectionPagerAdapter(getSupportFragmentManager(), mArticleRealmResults));
        mArticlesViewPager.setCurrentItem(getIntent().getIntExtra(EXTRA_ARTICLE_POSITION, 0));

    }

    @SuppressWarnings("deprecation")
    @OnClick(R.id.share_fab)
    public void onClick() {
        Article article = mArticleRealmResults.get(mArticlesViewPager.getCurrentItem());
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(article.getTitle() + "\n" + Html.fromHtml(article.getBody()) + "\n#XYZ Reader")
                .getIntent(), getString(R.string.action_share)));
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mRealm != null) {
            if (!mRealm.isClosed()) {
                mRealm.close();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRealm != null) {
            mRealm = Realm.getDefaultInstance();
        }
    }
}
