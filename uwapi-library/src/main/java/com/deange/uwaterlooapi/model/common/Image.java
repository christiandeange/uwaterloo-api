package com.deange.uwaterlooapi.model.common;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Image extends BaseModel {

    @SerializedName("id")
    private int mId;

    @SerializedName("file")
    private String mFile;

    @SerializedName("alt")
    private String mAltText;

    @SerializedName("mime")
    private String mMimeType;

    @SerializedName("size")
    private int mSizeBytes;

    @SerializedName("width")
    private int mWidth;

    @SerializedName("height")
    private int mHeight;

    @SerializedName("url")
    private String mUrl;

    /**
     * Unique id of image
     */
    public int getId() {
        return mId;
    }

    /**
     * Relative link to image file path in filename.{format}
     */
    public String getFile() {
        return mFile;
    }

    /**
     * Image alternate text
     */
    public String getAltText() {
        return mAltText;
    }

    /**
     * Image MIME type in "string/{format}"
     */
    public String getMimeType() {
        return mMimeType;
    }

    /**
     * Image file size in bytes
     */
    public int getSizeInBytes() {
        return mSizeBytes;
    }

    /**
     * Image width in pixels
     */
    public int getWidth() {
        return mWidth;
    }

    /**
     * Image height in pixels
     */
    public int getHeight() {
        return mHeight;
    }

    /**
     * Full link to image resource
     */
    public String getUrl() {
        return mUrl;
    }

}
