package onl.fdt.java.android.fdtsvideofeed.api.payload;

import com.google.gson.annotations.SerializedName;

public class VideoEntity {

    @SerializedName("_id")
    private String id;
    @SerializedName("feedurl")
    private String feedUrl;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("description")
    private String description;
    @SerializedName("likecount")
    private Long likeCount;
    @SerializedName("avatar")
    private String avatar;

    public String getId() {
        return id;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDescription() {
        return description;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "VideoEntity{" +
                "_id='" + id + '\'' +
                ", feedurl='" + feedUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                ", description='" + description + '\'' +
                ", likecount=" + likeCount +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public String getLeftText() {
        return this.nickname + "\n\n" +
                this.description;
    }

    public VideoEntity(String id, String feedUrl, String nickname, String description, Long likeCount, String avatar) {
        this.id = id;
        this.feedUrl = feedUrl;
        this.nickname = nickname;
        this.description = description;
        this.likeCount = likeCount;
        this.avatar = avatar;
    }
}
