package com.marcosoft;

import com.marcosoft.storage.AmazonS3Config;
import com.marcosoft.storage.AmazonS3Service;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        AmazonS3Config s3Config = new AmazonS3Config("a-user-jira-sw-dps-file-attacher-nprod-0",
                "lLxOe+CEB5GrQYCXKFeLbvyqOE8hFg/aHvXslHfu",
                "https://s3.sicredi.net",
                "jira-sw-dps-file-attacher-nprod-0/");
        AmazonS3Service s3Service = new AmazonS3Service(s3Config);



        // Create a JFrame (window)
        JFrame frame = new JFrame("My Window");

        // Create a DefaultListModel to hold the data for the JList
        DefaultListModel<String> listModel = new DefaultListModel<>();

        s3Service.listS3Objects().forEach(listModel::addElement);

        // Create a JList with the DefaultListModel
        JList<String> itemList = new JList<>(listModel);

        // Create a JScrollPane to add scroll functionality if needed
        JScrollPane scrollPane = new JScrollPane(itemList);

        // Add the JScrollPane to the content pane of the frame
        frame.getContentPane().add(scrollPane);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        frame.setSize(300, 200);


        // Set the frame to be visible
        frame.setVisible(true);



    }
}