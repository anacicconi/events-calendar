package com.cicconi.events.model;

import com.cicconi.events.database.Event;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;
import java.util.Date;

public class RecordResponse {

    @SerializedName("datasetid")
    @Expose
    private String datasetid;
    @SerializedName("recordid")
    @Expose
    private String recordid;
    @SerializedName("fields")
    @Expose
    private FieldsResponse fields;
    @SerializedName("geometry")
    @Expose
    private GeometryResponse geometry;
    @SerializedName("record_timestamp")
    @Expose
    private String recordTimestamp;

    public String getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(String datasetid) {
        this.datasetid = datasetid;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public FieldsResponse getFields() {
        return fields;
    }

    public void setFields(FieldsResponse fields) {
        this.fields = fields;
    }

    public GeometryResponse getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryResponse geometry) {
        this.geometry = geometry;
    }

    public String getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(String recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public Event toEvent() {

        return new Event(
            this.recordid,
            this.fields.getAccessType(),
            this.fields.getAddressStreet(),
            this.fields.getLatLon().get(0),
            this.fields.getLatLon().get(1),
            this.fields.getCategory(),
            this.fields.getTitle(),
            this.fields.getCoverAlt(),
            this.fields.getDateStart().getTime(),
            this.fields.getAddressName(),
            this.fields.getDescription(),
            this.fields.getContactMail(),
            this.fields.getLeadText(),
            this.fields.getCoverUrl(),
            this.fields.getAddressCity(),
            this.fields.getPriceType(),
            this.fields.getUrl(),
            this.fields.getDateDescription(),
            this.fields.getAddressZipcode(),
            this.fields.getPriceDetail(),
            this.fields.getContactPhone(),
            this.fields.getContactUrl(),
            this.fields.getAccessMail(),
            this.fields.getContactFacebook(),
            this.fields.getAccessPhone(),
            false
        );
    }
}
