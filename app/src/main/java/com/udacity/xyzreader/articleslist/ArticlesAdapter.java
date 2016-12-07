package com.udacity.xyzreader.articleslist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.udacity.xyzreader.R;
import com.udacity.xyzreader.Util;
import com.udacity.xyzreader.articledetails.ArticleDetailsActivity;
import com.udacity.xyzreader.beans.Article;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class ArticlesAdapter extends RealmRecyclerViewAdapter<Article, ArticlesAdapter.ArticleViewHolder> {


    public ArticlesAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Article> data) {
        super(context, data, true);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = getData().get(position);
        Glide.with(context)
                .load(article.getThumb())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_photo_place_holder)
                .into(holder.mArticleThumbnailImageView);
        holder.mArticleTitleTextView.setText(article.getTitle());
        holder.mArticleSubTitleTextView.setText(
                Util.formatDate(article.getPublishedDate()) +
                        " by " +
                        article.getAuthor()
        );
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.article_thumbnail_image_view)
        ImageView mArticleThumbnailImageView;
        @BindView(R.id.article_title_text_view)
        TextView mArticleTitleTextView;
        @BindView(R.id.article_sub_title_text_view)
        TextView mArticleSubTitleTextView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ArticleDetailsActivity.class);
            intent.putExtra(ArticleDetailsActivity.EXTRA_ARTICLE_POSITION, getLayoutPosition());
            context.startActivity(intent);
        }
    }

}
