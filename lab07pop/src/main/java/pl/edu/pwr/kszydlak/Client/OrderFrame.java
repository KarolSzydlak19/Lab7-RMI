package pl.edu.pwr.kszydlak.Client;

import interfaces.IShop;
import model.*;
import pl.edu.pwr.kszydlak.MyPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class OrderFrame extends JFrame implements ActionListener {
    Client client;
    MyPanel myPanel;
    JButton startb;
    String name;
    JButton makeOrder, seeOrders;
    JLabel clientName;
    ArrayList<JLabel> itemLabels;
    ArrayList<JButton> orderButtons;
    int clientId;
    List<Order> orders = new ArrayList<>();
    List<OrderLine> orderLines = new ArrayList<>();
    List<SubmittedOrder> submittedOrders = new ArrayList<>();

    IShop server;
    public OrderFrame(Client client, IShop server, int clientId) throws RemoteException {
        this.client = client;
        this.clientId = clientId;
        this.setTitle("Orders Made");
        submittedOrders = server.getSubmittedOrders();
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
        seeOrders.setText("Refresh");
        seeOrders.setBounds(110,0,120,30);
        seeOrders.addActionListener(this);
        //this.add(makeOrder);
        this.add(seeOrders);
        JLabel newName = new JLabel();
        newName.setText("Name");
        newName.setBounds(10,50,100,20);
        JLabel newCat = new JLabel();
        newCat.setText("Category");
        newCat.setBounds(110,50,100,20);
        JLabel newPrice = new JLabel();
        newPrice.setText("Price");
        newPrice.setBounds(210,50,100,20);

        JLabel newQuantity = new JLabel();
        newQuantity.setText("Quantity");
        newQuantity.setBounds(310,50,100,20);
        this.add(newQuantity);

        this.add(newName);
        this.add(newCat);
        this.add(newPrice);
        viewStock();
        startb = new JButton();
        startb.setBounds(170,300,100,50);
        startb.setText("start");
        //startb.addActionListener(this);
        //this.add(startb);
        myPanel = new MyPanel();
        this.add(myPanel);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }
    public void viewStock() throws RemoteException {
        int x=10;
        int y=70;
        for (int i=0;i<submittedOrders.size();i++){
            orders.clear();
            orderLines.clear();
            List<OrderLine> o = new ArrayList<>();
            if(submittedOrders.get(i).getOrder().getClientID()==clientId){
                orders.add(submittedOrders.get(i).getOrder());
                for (int j=0; j<orders.size();j++){
                    o = orders.get(j).getOll();
                    for (int k=0;k<o.size();k++){
                        orderLines.add(o.get(k));
                    }
                }
                JLabel ordernum = new JLabel();
                ordernum.setText("Order: "+submittedOrders.get(i).getId());
                ordernum.setBounds(x,y,100,30);
                this.add(ordernum);

                JLabel orderStat = new JLabel();
                orderStat.setText(String.valueOf("Status: "+submittedOrders.get(i).getStatus()));
                orderStat.setBounds(x+100,y,200,30);
                this.add(orderStat);
                y+=30;
                //tu
                for(int l=0;l<orderLines.size();l++){
                    JLabel itemName = new JLabel();
                    itemName.setText(orderLines.get(l).getIt().getName());
                    itemName.setBounds(x,y,100,30);
                    this.add(itemName);
                    x+=100;

                    JLabel itemCat = new JLabel();
                    itemCat.setText(String.valueOf(orderLines.get(l).getIt().getCategory()));
                    itemCat.setBounds(x,y,100,30);
                    this.add(itemCat);
                    x+=100;

                    JLabel itemPrice = new JLabel();
                    itemPrice.setText(String.valueOf(orderLines.get(l).getIt().getPrice()));
                    itemPrice.setBounds(x,y,100,30);
                    this.add(itemPrice);
                    x+=100;

                    JLabel itemQuantity = new JLabel();
                    itemQuantity.setText(String.valueOf(orderLines.get(l).getQuantity()));
                    itemQuantity.setBounds(x,y,100,30);
                    this.add(itemQuantity);
                    x=10;
                    y+=30;

                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==seeOrders){
            try {
                new OrderFrame(client,server,clientId);
                this.dispose();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
