/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.observer;

/**
 * Permite reaccionar a cambios relevantes del almacenamiento compartido.
 */
public interface StorageObserver {

    void onStorageChanged(StorageEventType eventType);
}
