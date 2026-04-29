package com.example.exam.rmi;

import com.example.exam.model.RendezVous;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface NotificationRegistry {

    void registerListener(int patientId, NotificationListener listener);

    List<RendezVous> getAppointments(int patientId);

    void notifyPatient(int patientId, String message);
}
