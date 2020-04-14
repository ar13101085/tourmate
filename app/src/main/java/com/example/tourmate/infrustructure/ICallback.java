package com.example.tourmate.infrustructure;

public interface ICallback {
    <T> void receivedData(int resultCode,boolean isSuccess,T data);
}
