package com.example.exam.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotificationListener extends Remote {

    void onNotification(String message) throws RemoteException;
}
