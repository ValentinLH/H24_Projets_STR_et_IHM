package fr.polytech.vgl.network;

public interface NetworkObserver {
    void onObjectReceived(Object receivedObject);
    default public void onNetworkError(Exception e) {
        // Gérez les erreurs réseau
        //e.printStackTrace();
    }
}
