package com.java.grpc.adapter.in.web.request;

public class PingWebRequest {

    private String address;
    private int time;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
