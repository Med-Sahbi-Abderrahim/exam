package com.example.exam.rmi;

import com.example.exam.model.RendezVous;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NotificationService extends Remote {

    void registerListener(int patientId, NotificationListener listener) throws RemoteException;

    List<RendezVous> getAppointments(int patientId) throws RemoteException;
}
