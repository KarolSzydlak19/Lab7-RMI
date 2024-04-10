package pl.edu.pwr.kszydlak.Shop;

import model.Client;
import model.ItemType;
import pl.edu.pwr.kszydlak.MyPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ServerFrame extends JFrame implements ActionListener {
    MyPanel myPanel;
    JButton startb;
    String name;
    JTextField nazwa;
    List<ItemType> items;
    ArrayList<JLabel> descriptions;


    public ServerFrame(List<ItemType> items){
        this.setTitle("Stock");
        this.items = items;
        descriptions = new ArrayList<>();
        int x =0;
        int y=30;
        JLabel newName = new JLabel();
        newName.setText("Name");
        newName.setBounds(0,0,100,20);
        JLabel newCat = new JLabel();
        newCat.setText("Category");
        newCat.setBounds(100,0,100,20);
        JLabel newPrice = new JLabel();
        newPrice.setText("Price");
        newPrice.setBounds(200,0,100,20);
        this.add(newName);
        this.add(newCat);
        this.add(newPrice);
        for (int i=0;i<items.size();i++){
            JLabel newItemName = new JLabel();
            newItemName.setText(items.get(i).getName());
            newItemName.setBounds(x,y,100,20);
            descriptions.add(newItemName);
            this.add(newItemName);
            x+=100;
            JLabel newItemCat = new JLabel();
            newItemCat.setText(String.valueOf(items.get(i).getCategory()));
            newItemCat.setBounds(x,y,100,20);
            descriptions.add(newItemCat);
            this.add(newItemCat);
            x+=100;
            JLabel newItemPrice = new JLabel();
            newItemPrice.setText(String.valueOf(items.get(i).getPrice()));
            newItemPrice.setBounds(x,y,100,20);
            descriptions.add(newItemPrice);
            this.add(newItemPrice);
            x=0;
            y+=30;
        }

        startb = new JButton();
        startb.setBounds(300,480,50,20);
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
            for (int i=0;i<ShopApp.submittedOrders.size();i++){
                System.out.println(ShopApp.submittedOrders.get(i).getStatus());
            }
        }
    }
}
