package pl.edu.pwr.kszydlak.Seller;

import interfaces.IShop;
import pl.edu.pwr.kszydlak.Shop.ServerFrame;
import pl.edu.pwr.kszydlak.Shop.ServerImpl;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SellerApp {
    static IShop server;
    static Registry registry;
    public static void main(String args[]) throws RemoteException, AlreadyBoundException, NotBoundException {
        System.setProperty("java.security.policy","java.policy");
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new RMISecurityManager());
        }
        registry = LocateRegistry.getRegistry("localhost",1234);
        server = (IShop)registry.lookup("Server");
        new SellerFrame(server);
    }
}
