package com.ittianyu.relight.lcee.bean;

/**
 * 网络数据实体
 * <p>
 * Created by liyujiang on 2018/11/16 15:31
 */
public class GirlItemBean extends JavaBean {
    private String thumbURL;
    private String middleURL;
    private String largeTnImageUrl;
    private String objURL;
    private String fromURLHost;
    private int width;
    private int height;
    private String type;
    private int is_gif;

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getMiddleURL() {
        return middleURL;
    }

    public void setMiddleURL(String middleURL) {
        this.middleURL = middleURL;
    }

    public String getLargeTnImageUrl() {
        return largeTnImageUrl;
    }

    public void setLargeTnImageUrl(String largeTnImageUrl) {
        this.largeTnImageUrl = largeTnImageUrl;
    }

    public String getObjURL() {
        return objURL;
    }

    public void setObjURL(String objURL) {
        this.objURL = objURL;
    }

    public String getFromURLHost() {
        return fromURLHost;
    }

    public void setFromURLHost(String fromURLHost) {
        this.fromURLHost = fromURLHost;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIs_gif() {
        return is_gif;
    }

    public void setIs_gif(int is_gif) {
        this.is_gif = is_gif;
    }

}
