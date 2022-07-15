package com.norriszhang.customerletters.views;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainPanel extends JPanel {
    private JTextField customerName;
    private JTextField customerAddress;
    public MainPanel() {
        JLabel customerNameLabel = new JLabel("Customer Name:");
        add(customerNameLabel);
        customerName = new JTextField(10);
        add(customerName);
        JLabel customerAddressLabel = new JLabel("Address:");
        add(customerAddressLabel);
        customerAddress = new JTextField(10);
        add(customerAddress);
    }
    public String getCustomerName() {
        return customerName.getText();
    }
    public String getCustomerAddress() {
        return customerAddress.getText();
    }
}
