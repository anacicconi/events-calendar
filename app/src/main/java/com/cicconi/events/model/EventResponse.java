package com.cicconi.events.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventResponse {

    @SerializedName("nhits")
    @Expose
    private Integer nhits;
    @SerializedName("parameters")
    @Expose
    private ParametersResponse parameters;
    @SerializedName("records")
    @Expose
    private List<RecordResponse> records = null;

    public Integer getNhits() {
        return nhits;
    }

    public void setNhits(Integer nhits) {
        this.nhits = nhits;
    }

    public ParametersResponse getParameters() {
        return parameters;
    }

    public void setParameters(ParametersResponse parameters) {
        this.parameters = parameters;
    }

    public List<RecordResponse> getRecords() {
        return records;
    }

    public void setRecords(List<RecordResponse> records) {
        this.records = records;
    }

}
