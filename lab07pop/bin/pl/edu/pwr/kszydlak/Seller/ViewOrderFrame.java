package pl.edu.pwr.kszydlak.Seller;

import interfaces.IShop;
import model.Order;
import model.OrderLine;
import model.Status;
import model.SubmittedOrder;
import pl.edu.pwr.kszydlak.MyPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ViewOrderFrame extends JFrame implements ActionListener {
    MyPanel myPanel;
    JButton startb;
    String name;
    JTextField nazwa;
    List<SubmittedOrder> submittedOrders;
    IShop server;
    Order order;
    OrderLine orderLine;
    List<OrderLine> orderLines;

    List<JLabel> itemNames = new ArrayList<>();
    List<JLabel> itemCats = new ArrayList<>();
    List<JLabel> itemPrices = new ArrayList<>();
    List<JLabel> itemQuantities = new ArrayList<>();
    JButton changeStatus;
    int orderId;
    JLabel status;

    public ViewOrderFrame(IShop server, SubmittedOrder submittedOrder, int orderId) throws RemoteException {
        this.server = server;
        this.orderId = orderId;
        this.order = submittedOrder.getOrder();
        this.orderLines = order.getOll();
        submittedOrders = server.getSubmittedOrders();
        JLabel idLaebel = new JLabel();
        idLaebel.setText("Order id:"+submittedOrder.getId());
        idLaebel.setBounds(10,0,100,30);
        this.add(idLaebel);

        JLabel productName = new JLabel();
        productName.setText("Name");
        productName.setBounds(10,30,100,30);
        this.add(productName);

        JLabel productCat = new JLabel();
        productCat.setText("Category");
        productCat.setBounds(110,30,100,30);
        this.add(productCat);

        JLabel productPrice = new JLabel();
        productPrice.setText("Price");
        productPrice.setBounds(210,30,100,30);
        this.add(productPrice);

        JLabel productQuantity = new JLabel();
        productQuantity.setText("Quantity");
        productQuantity.setBounds(310,30,100,30);
        this.add(productQuantity);

        int x = 10;
        int y =60;
        for (int i=0;i<orderLines.size();i++){
            JLabel name = new JLabel();
            name.setText(orderLines.get(i).getIt().getName());
            name.setBounds(x,y,100,30);
            itemNames.add(name);
            this.add(name);
            x+=100;
            JLabel cat = new JLabel();
            cat.setText(String.valueOf(orderLines.get(i).getIt().getCategory()));
            cat.setBounds(x,y,100,30);
            itemCats.add(cat);
            this.add(cat);
            x+=100;

            JLabel price = new JLabel();
            price.setText(String.valueOf(orderLines.get(i).getIt().getPrice()));
            price.setBounds(x,y,100,30);
            itemPrices.add(price);
            this.add(price);
            x+=100;

            JLabel quantity = new JLabel();
            quantity.setText(String.valueOf(orderLines.get(i).getQuantity()));
            quantity.setBounds(x,y,100,30);
            itemQuantities.add(quantity);
            this.add(quantity);
            y+=30;
            x=10;
        }
        JLabel totalPrice = new JLabel();
        float money=0;
        for (int j=0;j<itemPrices.size();j++){
            float a = 0;
            a = Float.parseFloat(itemPrices.get(j).getText())*Integer.parseInt(itemQuantities.get(j).getText());
            money+=a;
        }
        totalPrice.setText("Total price: "+String.valueOf(money));
        totalPrice.setBounds(x,y,200,30);
        this.add(totalPrice);

        status = new JLabel();
        status.setText("Status: "+submittedOrder.getStatus());
        status.setBounds(x+150,y,200,30);
        this.add(status);

        y+=30;

        changeStatus = new JButton();
        changeStatus.setText("Change Status");
        changeStatus.setBounds(x,y,200,30);
        changeStatus.addActionListener(this);
        this.add(changeStatus);



        this.setTitle("Order "+submittedOrder.getId());
        myPanel = new MyPanel();
        this.add(myPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==changeStatus){
            if (submittedOrders.get(orderId).getStatus().equals(Status.PROCESSING)){
                try {
                    server.setStatus(orderId,Status.READY);
                    status.setText("READY");

                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (submittedOrders.get(orderId).getStatus().equals(Status.READY)){
                try {
                    server.setStatus(orderId,Status.DELIVERED);
                    status.setText("DELIVERED");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                submittedOrders = server.getSubmittedOrders();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
