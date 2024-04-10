package pl.edu.pwr.kszydlak.Client;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.Status;
import pl.edu.pwr.kszydlak.Shop.ServerImpl;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class ClientApp implements IStatusListener {

    @Override
    public void statusChanged(int id, Status status) throws RemoteException {

    }
    public static void main(String args[]) throws RemoteException, NotBoundException {
        System.setProperty("java.security.policy","java.policy");
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new RMISecurityManager());
        }
        new ClientFrame();
    }
}
