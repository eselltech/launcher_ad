package com.esell.esellbanner;

public class BannerBean {

    private String mediaType;//媒体类型
    private String mediaPath;//媒体路径
    private String mediaId;//媒体id
    private int duration;//播放时间

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "BannerBean{" +
                "mediaType='" + mediaType + '\'' +
                ", mediaPath='" + mediaPath + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", duration=" + duration +
                '}';
    }
}
