package com.java.grpc.adapter.in.web.response;

public class PingWebResponse {

    private String address;
    private int time;

    public PingWebResponse() {
    }

    public PingWebResponse(String address, int time) {
        this.address = address;
        this.time = time;
    }

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
