package com.example.exam.rmi;

import com.example.exam.model.RendezVous;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Standalone RMI client for demo. Run after WildFly is up, e.g.:
 * {@code java -cp target/classes com.example.exam.rmi.PatientClient}
 */
public final class PatientClient {

    private PatientClient() {
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("java.rmi.server.hostname", "localhost");

        int patientId = 1;

        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        NotificationService service = (NotificationService) registry.lookup("NotificationService");

        NotificationListener listener = (NotificationListener) UnicastRemoteObject.exportObject(
                new NotificationListener() {
                    @Override
                    public void onNotification(String message) throws RemoteException {
                        System.out.println("[callback] " + message);
                    }
                },
                0);

        service.registerListener(patientId, listener);

        List<RendezVous> rdvs = service.getAppointments(patientId);
        System.out.println("Appointments for patient " + patientId + ":");
        for (RendezVous r : rdvs) {
            System.out.println("  " + r.getId() + " | " + r.getDateRendezVous() + " | " + r.getStatut() + " | " + r.getMotif());
        }

        System.out.println("Waiting for notifications (Enter to exit)...");
        System.in.read();
    }
}
