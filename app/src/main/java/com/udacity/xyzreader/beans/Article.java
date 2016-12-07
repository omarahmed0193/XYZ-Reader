package com.udacity.xyzreader.beans;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Article extends RealmObject implements Parcelable {


    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("aspect_ratio")
    @Expose
    private Double aspectRatio;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("body")
    @Expose
    private String body;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return The thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * @param thumb The thumb
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * @return The aspectRatio
     */
    public Double getAspectRatio() {
        return aspectRatio;
    }

    /**
     * @param aspectRatio The aspect_ratio
     */
    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    /**
     * @return The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The publishedDate
     */
    public String getPublishedDate() {
        return publishedDate;
    }

    /**
     * @param publishedDate The published_date
     */
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    /**
     * @return The body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.photo);
        dest.writeString(this.thumb);
        dest.writeValue(this.aspectRatio);
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeString(this.publishedDate);
        dest.writeString(this.body);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.id = in.readString();
        this.photo = in.readString();
        this.thumb = in.readString();
        this.aspectRatio = (Double) in.readValue(Double.class.getClassLoader());
        this.author = in.readString();
        this.title = in.readString();
        this.publishedDate = in.readString();
        this.body = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
