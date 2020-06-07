/*
 * Copyright (c) 2020 fdt <frederic.dt.twh@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
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
