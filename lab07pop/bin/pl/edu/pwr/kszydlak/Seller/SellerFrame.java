package pl.edu.pwr.kszydlak.Seller;

import interfaces.IShop;
import model.Client;
import model.Order;
import model.Status;
import model.SubmittedOrder;
import pl.edu.pwr.kszydlak.Client.ClientFrame2;
import pl.edu.pwr.kszydlak.MyPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class SellerFrame extends JFrame implements ActionListener {
    MyPanel myPanel;
    JButton startb;
    String name;
    JTextField nazwa;
    List<SubmittedOrder> submittedOrders;
    IShop server;
    ArrayList<JLabel> clientsID;
    ArrayList<JButton> viewOrders;
    JButton refresh;

    public SellerFrame(IShop server) throws RemoteException {
        this.server = server;
        submittedOrders = server.getSubmittedOrders();
        clientsID = new ArrayList<>();
        viewOrders = new ArrayList<>();

        JLabel clientId = new JLabel();
        clientId.setText("Order Id");
        int x = 10;
        int y = 0;
        clientId.setBounds(x,y,100,30);
        this.add(clientId);
        y+=30;
        for(int i=0;i<10;i++){
            JLabel idLabel = new JLabel();
            idLabel.setText(" ");
            idLabel.setBounds(x,y,100,30);
            if (i<submittedOrders.size()){
                idLabel.setText(String.valueOf(submittedOrders.get(i).getId()));
            }
            this.add(idLabel);
            clientsID.add(clientId);
            x+=100;

            JButton viewOrder = new JButton();
            viewOrder.setText("View");
            viewOrder.setBounds(x,y,100,30);
            viewOrder.addActionListener(this);
            if(!idLabel.getText().equals(" ")){
                viewOrders.add(viewOrder);
                this.add(viewOrder);
            }
            y+=30;
            x=10;
        }
        refresh = new JButton();
        refresh.setText("Refresh");
        refresh.setBounds(300,150,100,30);
        refresh.addActionListener(this);
        this.add(refresh);


        this.setTitle("Seller View");
        myPanel = new MyPanel();
        this.add(myPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==refresh){
            try {
                new SellerFrame(server);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
        }
        for (int j=0;j<viewOrders.size();j++){
            if (e.getSource()==viewOrders.get(j)){
                try {
                    submittedOrders = server.getSubmittedOrders();
                    if(submittedOrders.get(j).getStatus()==Status.NEW){
                        server.setStatus(j, Status.PROCESSING);
                        submittedOrders = server.getSubmittedOrders();
                        System.out.println(submittedOrders);
                    }
                    new ViewOrderFrame(server,submittedOrders.get(j),j);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
