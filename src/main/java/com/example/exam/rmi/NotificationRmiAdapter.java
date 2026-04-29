package com.example.exam.rmi;

import com.example.exam.model.RendezVous;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Objet RMI exporté : délègue au bean EJB (les beans EJB ne doivent pas implémenter {@link java.rmi.Remote}).
 */
public class NotificationRmiAdapter extends UnicastRemoteObject implements NotificationService {

    private static final long serialVersionUID = 1L;

    private final NotificationRegistry delegate;

    public NotificationRmiAdapter(NotificationRegistry delegate) throws RemoteException {
        super();
        this.delegate = delegate;
    }

    @Override
    public void registerListener(int patientId, NotificationListener listener) throws RemoteException {
        try {
            delegate.registerListener(patientId, listener);
        } catch (RuntimeException e) {
            throw new RemoteException("registerListener: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @Override
    public List<RendezVous> getAppointments(int patientId) throws RemoteException {
        try {
            return delegate.getAppointments(patientId);
        } catch (RuntimeException e) {
            // Ne pas attacher la cause Hibernate : le client RMI n'a pas ces classes sur le classpath.
            throw new RemoteException(
                    "getAppointments: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
