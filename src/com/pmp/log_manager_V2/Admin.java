package com.pmp.log_manager_V2;

import java.io.File;


public class Admin extends Thread {
    File fileNames[];
    ThreadGroup tgFiles = new ThreadGroup("File Processing Threads");

    public void run() {
        try {
            System.out.println("Fetching files...");
            fetchAllFiles("/home/local/ZOHOCORP/somesh-pt2166/Documents/dump/logs");
            System.out.println("Files Fetched in the path is Successfully\n\tPreparing DataStore........");
            System.out.println("Creating Threads......");

            for (File i : fileNames) {
                Thread fth = new FileActions(tgFiles, i);
                fth.setName(i.getName());
                System.out.println("\t\t" + fth.getName() + "  is Created and grouped under " + fth.getThreadGroup());
                fth.start();

            }
            Admin.sleep(250);
            System.out.println("File are Processed and Info are Stored in a Data Store\n ThankYou");
            System.out.print("Loading main.");
            for (int i = 0; i < 40; i++) {
                sleep(100);
                System.out.print(".");
            }
            System.out.println();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    void fetchAllFiles(String path) {

        File file = new File(path);
        if (file.isDirectory() && file.exists()) {
            fileNames = file.listFiles();
            System.out.println("Files in the directory " + file.getName() + " present in the path " + file.getAbsolutePath() + " are fetched sucessfully");
        }

    }

}
