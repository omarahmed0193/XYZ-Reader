package com.udacity.xyzreader.articledetails;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.udacity.xyzreader.R;
import com.udacity.xyzreader.Util;
import com.udacity.xyzreader.beans.Article;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticleDetailsFragment extends Fragment {


    public static final String ARGS_ARTICLE = "args_article";
    @BindView(R.id.article_photo_image_view)
    ImageView mArticlePhotoImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.article_text_view)
    TextView mArticleTextView;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.article_title_text_view)
    TextView mArticleTitleTextView;
    @BindView(R.id.article_sub_title_text_view)
    TextView mArticleSubTitleTextView;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.header_container)
    LinearLayout mHeaderContainer;
    private Article mArticle;

    public static ArticleDetailsFragment newInstance(Article article) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_ARTICLE, article);
        ArticleDetailsFragment fragment = new ArticleDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article_details, container, false);
        ButterKnife.bind(this, rootView);

        mArticle = getArguments().getParcelable(ARGS_ARTICLE);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        Glide.with(getActivity())
                .load(mArticle.getPhoto())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(mArticlePhotoImageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        Palette p = Palette.generate(resource, 12);
                        mHeaderContainer.setBackgroundColor(p.getDarkMutedColor(0xFF333333));
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mArticleTextView.setText(Html.fromHtml(mArticle.getBody(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            mArticleTextView.setText(Html.fromHtml(mArticle.getBody()));
        }

        mArticleTitleTextView.setText(mArticle.getTitle());
        mArticleSubTitleTextView.setText(
                Util.formatDate(mArticle.getPublishedDate()) +
                        " by " +
                        mArticle.getAuthor()
        );

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mToolbarLayout.setTitle(mArticle.getTitle());
                    isShow = true;
                } else if (isShow) {
                    mToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });


        return rootView;
    }
}
