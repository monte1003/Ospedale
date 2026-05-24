package packagee.ospedale.observer;

/**
 * Permite reaccionar a cambios relevantes del almacenamiento compartido.
 */
public interface StorageObserver {

    void onStorageChanged(StorageEventType eventType);
}
