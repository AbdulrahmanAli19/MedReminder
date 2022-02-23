package com.example.itijavaproject.pojo.model;

import java.util.ArrayList;
import java.util.List;

public class ListOfRequest {
    private List<Request> requestList=new ArrayList<>();
   public ListOfRequest(){}

    public ListOfRequest(List<Request> requestList) {
        this.requestList = requestList;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }
}
