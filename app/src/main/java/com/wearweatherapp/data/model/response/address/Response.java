
package com.wearweatherapp.data.model.response.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("service")
    @Expose
    private Service service;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("record")
    @Expose
    private Record record;
    @SerializedName("page")
    @Expose
    private Page page;
    @SerializedName("result")
    @Expose
    private Result result;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
