package com.tensun.digitalscreen.socket;


public interface IServer {
    void rxData(String data, String ip);
    void addClient(String ip);
    void removeClient(String ip);

}
