package pl.edu.pwr.kszydlak.Client;

import interfaces.IStatusListener;
import model.Status;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ClientImpl implements IStatusListener, Serializable {
    JLabel notification;
    JLabel substatus;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    private int clientId;
    public ClientImpl(JLabel notification, JLabel substatus) throws RemoteException {
        UnicastRemoteObject.exportObject(this,0);
        this.notification = notification;
        this.substatus = substatus;
    }
    @Override
    public void statusChanged(int i, Status status) throws RemoteException {
        if (substatus.getText().equals("Subscribed")){
            notification.setText("Order "+i+"status changed to: "+status);
        }
    }

}
