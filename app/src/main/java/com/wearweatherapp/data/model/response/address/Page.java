
package com.wearweatherapp.data.model.response.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("current")
    @Expose
    private String current;
    @SerializedName("size")
    @Expose
    private String size;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
