package com.marcosoft;

import com.marcosoft.storage.AmazonS3Config;
import com.marcosoft.storage.AmazonS3Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI(){

        JFrame frame = new JFrame("Marcosoft S3 Broswer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setLayout(new GridLayout(0,1,30,30));

        JPanel panelOptions = new JPanel();
        panelOptions.setLayout(new GridLayout(0,1,0,0));

        JLabel jLabelBucketName = new JLabel("Bucket name:");
        JLabel jLabelAccessKey = new JLabel("Access key:");
        JLabel jLabelSecretKey = new JLabel("Secret key:");
        JLabel jLabelEndPoint = new JLabel("End point:");

        JTextField jTextBucketName = new JTextField("jira-sw-dps-file-attacher-nprod-0/");
        JTextField jTextAccessKey = new JTextField("a-user-jira-sw-dps-file-attacher-nprod-0");
        JTextField jTextSecretKey = new JTextField("lLxOe+CEB5GrQYCXKFeLbvyqOE8hFg/aHvXslHfu");
        JTextField jTextEndPoint = new JTextField("");

        JButton jButtonGetFiles = new JButton("Get Files");

        panelOptions.add(jLabelBucketName);
        panelOptions.add(jTextBucketName);
        panelOptions.add(jLabelAccessKey);
        panelOptions.add(jTextAccessKey);
        panelOptions.add(jLabelSecretKey);
        panelOptions.add(jTextSecretKey);
        panelOptions.add(jLabelEndPoint);
        panelOptions.add(jTextEndPoint);
        panelOptions.add(jButtonGetFiles);

        JPanel panelList = new JPanel();
        //panelList.setSize(700,300);

        // Create a DefaultListModel to hold the data for the JList
        DefaultListModel<String> listModel = new DefaultListModel<>();


        // Create a JList with the DefaultListModel
        JList<String> itemList = new JList<>(listModel);

        // Create a JScrollPane to add scroll functionality if needed
        JScrollPane scrollPane = new JScrollPane(itemList);

        panelList.add(scrollPane);



        jButtonGetFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AmazonS3Config s3Config = new AmazonS3Config(jTextAccessKey.getText(),
                        jTextSecretKey.getText(),
                        jTextEndPoint.getText(),
                        jTextBucketName.getText());
                AmazonS3Service s3Service = new AmazonS3Service(s3Config);

                s3Service.listS3Objects().forEach(listModel::addElement);
            }
        });

        // Add the JScrollPane to the content pane of the frame
        //frame.getContentPane().add(scrollPane);

        frame.add(panelOptions);
        frame.add(panelList);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        frame.setSize(700, 500);


        // Set the frame to be visible
        frame.setVisible(true);


    }
}