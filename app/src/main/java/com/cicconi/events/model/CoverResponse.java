package com.cicconi.events.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoverResponse {

    @SerializedName("mimetype")
    @Expose
    private String mimetype;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("color_summary")
    @Expose
    private List<String> colorSummary = null;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("thumbnail")
    @Expose
    private Boolean thumbnail;

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<String> getColorSummary() {
        return colorSummary;
    }

    public void setColorSummary(List<String> colorSummary) {
        this.colorSummary = colorSummary;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Boolean thumbnail) {
        this.thumbnail = thumbnail;
    }
}
