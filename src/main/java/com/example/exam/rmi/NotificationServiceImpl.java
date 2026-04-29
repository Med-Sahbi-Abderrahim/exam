package com.example.exam.rmi;

import com.example.exam.model.Medecin;
import com.example.exam.model.Patient;
import com.example.exam.model.RendezVous;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Startup
public class NotificationServiceImpl implements NotificationRegistry {

    private static final int RMI_PORT = 1099;
    private static final String BIND_NAME = "NotificationService";

    private final Map<Integer, NotificationListener> listeners = new ConcurrentHashMap<>();

    /**
     * Même approche que {@link com.example.exam.ejb.RendezVousEJB} : EMF depuis persistence.xml.
     * Sur WildFly, {@code @PersistenceUnit} + {@code createEntityManager()} peut ne pas utiliser la même
     * base que l’app (effets de JTA / datasource par défaut) — d’où des erreurs type H2 vide.
     */
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    private NotificationRmiAdapter exportedAdapter;

    @PostConstruct
    public void init() {
        System.setProperty("java.rmi.server.hostname", "localhost");
        try {
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(RMI_PORT);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(RMI_PORT);
            }
            exportedAdapter = new NotificationRmiAdapter(this);
            // Pas d'exportObject ici : le constructeur UnicastRemoteObject() exporte déjà l'adaptateur.
            registry.rebind(BIND_NAME, exportedAdapter);
        } catch (Exception e) {
            throw new IllegalStateException("RMI registry start failed: " + e.getMessage(), e);
        }
    }

    @PreDestroy
    public void shutdown() {
        try {
            if (exportedAdapter != null) {
                UnicastRemoteObject.unexportObject(exportedAdapter, true);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void registerListener(int patientId, NotificationListener listener) {
        listeners.put(patientId, listener);
    }

    @Override
    public List<RendezVous> getAppointments(int patientId) {
        EntityManager em = emf.createEntityManager();
        try {
            List<RendezVous> list = em.createQuery(
                            "SELECT r FROM RendezVous r JOIN FETCH r.patient JOIN FETCH r.medecin "
                                    + "WHERE r.patient.id = :pid ORDER BY r.dateRendezVous DESC",
                            RendezVous.class)
                    .setParameter("pid", (long) patientId)
                    .getResultList();
            for (RendezVous r : list) {
                Patient p = r.getPatient();
                if (p != null) {
                    p.setRendezVous(null);
                }
                Medecin m = r.getMedecin();
                if (m != null) {
                    m.setRendezVous(null);
                }
            }
            return list;
        } finally {
            em.close();
        }
    }

    @Override
    public void notifyPatient(int patientId, String message) {
        NotificationListener listener = listeners.get(patientId);
        if (listener == null) {
            return;
        }
        try {
            listener.onNotification(message);
        } catch (RemoteException ignored) {
        }
    }
}
