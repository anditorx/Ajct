package com.anditorx.ajct.Model;

import java.util.List;

public class ResponseLaundryModel {

    private int code;
    private String message;
    private List<DataLaundryModel> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataLaundryModel> getData() {
        return data;
    }

    public void setData(List<DataLaundryModel> data) {
        this.data = data;
    }



}
