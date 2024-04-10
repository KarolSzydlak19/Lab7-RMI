package pl.edu.pwr.kszydlak.Client;

import interfaces.IShop;
import model.*;
import pl.edu.pwr.kszydlak.MyPanel;
import pl.edu.pwr.kszydlak.Shop.ShopApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ClientFrame2 extends JFrame implements ActionListener {
    Client client;
    MyPanel myPanel;
    JButton startb;
    String name;
    JButton makeOrder, seeOrders,subButton;
    JLabel clientName,subStatus;
    ArrayList<JLabel> itemLabels;
    ArrayList<JButton> orderButtons;
    ArrayList<ItemType> items;
    ArrayList<JTextField> quantities;
    int clientId;
    JButton orderButton;

    IShop server;
    public JLabel notification;
    ClientImpl clientImpl;

    public ClientFrame2(Client client, IShop server, int clientId) throws RemoteException, AlreadyBoundException {
        this.clientId = clientId;
        quantities = new ArrayList<>();
        this.items = (ArrayList<ItemType>) server.getItemList();
        this.setTitle("Client View");
        this.client=client;
        itemLabels = new ArrayList<>();
        orderButtons = new ArrayList<>();
        this.server = server;
        clientName = new JLabel();
        clientName.setText("User: "+client.getName());
        clientName.setBounds(300,0,200,30);
        this.add(clientName);
        /*makeOrder = new JButton();
        makeOrder.setText("View Stock");
        makeOrder.setBounds(0,0,100,30);
        makeOrder.addActionListener(this);*/
        seeOrders = new JButton();
        seeOrders.setText("View Orders");
        seeOrders.setBounds(150,0,120,30);
        seeOrders.addActionListener(this);
        subButton = new JButton();
        subButton.setText("Subscribe");
        subButton.setBounds(0,0,120,30);
        subButton.addActionListener(this);
        subStatus = new JLabel();
        subStatus.setText("Unsubscribed");
        subStatus.setBounds(0,40,100,20);

        JLabel message = new JLabel();
        message.setText("Messages:");
        message.setBounds(0,60,100,20);
        this.add(message);

        notification = new JLabel();
        notification.setText("No new messages");
        notification.setBounds(100,60,300,20);

        clientImpl = new ClientImpl(notification,subStatus);
        Registry registry = LocateRegistry.createRegistry(clientId);
        System.out.println(clientId);
        registry.bind("Client",clientImpl);
        server.subscribe(clientImpl,clientId);

        JButton nextMessage = new JButton();
        nextMessage.setText("Next");
        nextMessage.setBounds(0,80,100,20);
        nextMessage.addActionListener(this);
        this.add(nextMessage);
        this.add(notification);
        this.add(subStatus);
        this.add(subButton);

        orderButton = new JButton();
        orderButton.setText("Order");
        orderButton.setBounds(350,150,100,20);
        orderButton.addActionListener(this);
        this.add(orderButton);

        //this.add(makeOrder);
        this.add(seeOrders);
        JLabel newName = new JLabel();
        newName.setText("Name");
        newName.setBounds(0,120,100,20);
        JLabel newCat = new JLabel();
        newCat.setText("Category");
        newCat.setBounds(100,120,100,20);
        JLabel newPrice = new JLabel();
        newPrice.setText("Price");
        newPrice.setBounds(200,120,100,20);
        JLabel amount = new JLabel();
        amount.setText("Quantity");
        amount.setBounds(280,120,100,20);
        this.add(amount);
        this.add(newName);
        this.add(newCat);
        this.add(newPrice);
        viewStock();
        startb = new JButton();
        startb.setBounds(170,300,100,50);
        startb.setText("start");
        startb.addActionListener(this);
        //this.add(startb);
        myPanel = new MyPanel();
        this.add(myPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }
    public void viewStock() throws RemoteException {
        System.out.println("Pobieram ofertę");
        int x,y;
        x=0;
        y=140;
        for (int i=0;i<items.size();i++){
            JLabel newItemName = new JLabel();
            newItemName.setText(items.get(i).getName());
            newItemName.setBounds(x,y,100,20);
            itemLabels.add(newItemName);
            this.add(newItemName);
            x+=100;
            JLabel newItemCat = new JLabel();
            newItemCat.setText(String.valueOf(items.get(i).getCategory()));
            newItemCat.setBounds(x,y,100,20);
            itemLabels.add(newItemCat);
            this.add(newItemCat);
            x+=100;
            JLabel newItemPrice = new JLabel();
            newItemPrice.setText(String.valueOf(items.get(i).getPrice()));
            newItemPrice.setBounds(x,y,100,20);
            itemLabels.add(newItemPrice);
            this.add(newItemPrice);
            x+=90;
            JTextField amount = new JTextField();
            amount.setBounds(x,y,20,20);
            amount.setText("0");
            this.add(amount);
            quantities.add(amount);
            x+=30;
            /*JButton orderButton = new JButton();
            orderButton.setText("Order");
            orderButton.setBounds(x,y,100,20);
            orderButton.addActionListener(this);
            orderButtons.add(orderButton);
            this.add(orderButton);*/
            x=0;
            y+=30;
        }
    }
    public OrderLine placeOrder(ItemType item, int quantity){
        OrderLine orderLine = new OrderLine(item,quantity,item.getName());
        return orderLine;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==seeOrders){
            try {
                new OrderFrame(this.client,server,clientId);
                notification.setText("No new messages");
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==subButton){
            if (subStatus.getText().equals("Unsubscribed")) {
                subStatus.setText("Subscribed");
            }else {
                subStatus.setText("Unsubscribed");
            }
        }
        if(e.getSource()==orderButton){
            Order order = new Order(clientId);
            for(int i=0;i<items.size();i++){
                int quantity = Integer.parseInt(quantities.get(i).getText());
                if(quantity>0){
                    OrderLine orderLine = new OrderLine(items.get(i),quantity,items.get(i).getName());
                    order.addOrderLine(orderLine);
                }
            }
            try {
                server.placeOrder(order);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
