package pl.edu.pwr.kszydlak.Shop;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;

import java.rmi.AlreadyBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ShopApp {
    public static List<ItemType> items = new ArrayList<>();
    public static List<Client> clients = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();
    public static List<SubmittedOrder> submittedOrders = new ArrayList<>();
    public static  List<IStatusListener> subscribed = new ArrayList<>();


    public static void main(String args[]) throws RemoteException, AlreadyBoundException {
        //System.setProperty("java.rmi.server.hostname","1234");

        System.setProperty("java.security.policy","java.policy");
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new RMISecurityManager());
        }

        IShop server = new ServerImpl();
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.bind("Server",server);
        items = server.getItemList();
        for(int i=0;i<items.size();i++){
            System.out.println(items.get(i).getName());
        }
        System.out.println("ShopApp started");
        new ServerFrame(items);

    }
}
