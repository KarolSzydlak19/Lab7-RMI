package pl.edu.pwr.kszydlak.Shop;

import interfaces.IShop;
import interfaces.IStatusListener;
import model.*;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import static pl.edu.pwr.kszydlak.Shop.ShopApp.*;

public class ServerImpl implements IShop, Serializable {
    Registry registry;
    IStatusListener clientImpl;
    public ServerImpl() throws RemoteException {
        UnicastRemoteObject.exportObject(this,0);
    }
    @Override
    public int register(Client client) throws RemoteException {
        clients.add(client);
        System.out.println("Zarejestrowano klienta->"+client.getName());
        System.out.println(clients);
        int id = clients.size();
        return id;
    }

    @Override
    public List<ItemType> getItemList() throws RemoteException {
        List<ItemType> items = new ArrayList<>();
        ItemType i1 = new ItemType();
        i1.setName("Laptop");
        i1.setPrice(3050);
        i1.setCategory(0);
        items.add(i1);
        ItemType i2 = new ItemType();
        i2.setName("Mug");
        i2.setPrice(5);
        i2.setCategory(1);
        items.add(i2);
        ItemType i3 = new ItemType();
        i3.setName("Wallet");
        i3.setPrice(100);
        i3.setCategory(2);
        items.add(i3);
        return items;
    }

    @Override
    public int placeOrder(Order order) throws RemoteException {
        orders.add(order);
        SubmittedOrder submittedOrder = new SubmittedOrder(order);
        submittedOrders.add(submittedOrder);
        return 0;
    }

    @Override
    public List<SubmittedOrder> getSubmittedOrders() throws RemoteException {
        List<SubmittedOrder> submittedOrders1 = new ArrayList<>();
        submittedOrders1 = submittedOrders;
        return submittedOrders1;
    }

    @Override
    public boolean setStatus(int i, Status status) throws RemoteException {
        submittedOrders.get(i).setStatus(status);
        int b =submittedOrders.get(i).getId();
        int a = submittedOrders.get(i).getOrder().getClientID();
        for (i=0;i<subscribed.size();i++){

        }
        if(a==0){
            subscribed.get(0).statusChanged(b,status);
        }else {
            subscribed.get(a-1).statusChanged(b,status);
        }

        return true;
    }

    @Override
    public Status getStatus(int i) throws RemoteException {
        return null;
    }

    @Override
    public boolean subscribe(IStatusListener iStatusListener, int i) throws RemoteException {
        registry = LocateRegistry.getRegistry("localhost",i);
        System.out.println(i);
        try {
            clientImpl = (IStatusListener) registry.lookup("Client");
            subscribed.add(clientImpl);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean unsubscribe(int i) throws RemoteException {

        return false;
    }
}
