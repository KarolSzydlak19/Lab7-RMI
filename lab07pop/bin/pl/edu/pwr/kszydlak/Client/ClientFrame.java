package pl.edu.pwr.kszydlak.Client;

import interfaces.IShop;
import model.Client;
import model.Order;
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

public class ClientFrame extends JFrame implements ActionListener {
    MyPanel myPanel;
    JButton startb;
    String name;
    JTextField nazwa;
    IShop server;

    public ClientFrame(){
        this.setTitle("Login");
        JLabel login = new JLabel();
        login.setBounds(200,200,100,20);
        login.setText("login");
        this.add(login);
        nazwa = new JTextField();
        nazwa.setBounds(120,230,200,20);
        this.add(nazwa);
        startb = new JButton();
        startb.setBounds(170,300,100,50);
        startb.setText("start");
        startb.addActionListener(this);
        this.add(startb);
        myPanel = new MyPanel();
        this.add(myPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==startb){
            name = nazwa.getText();
            Client client = new Client();
            client.setName(name);
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry("localhost",1234);
                server = (IShop)registry.lookup("Server");
                System.out.println("Zarejestrowano");
                int id = server.register(client);
                new ClientFrame2(client,server,id);
                this.dispose();
            } catch (RemoteException | NotBoundException | AlreadyBoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
