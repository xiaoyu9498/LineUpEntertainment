package com.tensun.lineupentertainment.socket;

public interface IClient {

    void rxData(String var1);

    void onConnected();

    void onClosed();
}
