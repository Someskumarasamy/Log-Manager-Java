package com.pmp.log_manager;

import java.io.*;
import java.util.Date;


public class Startup {
    static File log = new File("./logs/Admin_log.txt");
    static File userlog = new File("./logs/User_log.txt");
    static Date date = new Date();
    Date dt = new Date();

    public static void main(String[] args) throws InterruptedException, IOException {

        if (!log.exists()) {
            log.createNewFile();
        }
        if (!userlog.exists()) {
            userlog.createNewFile();
        }

        try (FileWriter fw = new FileWriter(log.getAbsoluteFile(), true); BufferedWriter write = new BufferedWriter(fw)) {

            write.write("[" + Thread.currentThread().getName() + "][" + Thread.currentThread().getId() + "][" + date.toString() + "][" + Startup.class.getSimpleName() + "] : Started");
            write.newLine();

            System.out.println("Log Manger->Searches For Exceptions");
            Thread.sleep(300);
            System.out.println("The application is loading......");
            Thread.sleep(300);
            Thread admin = new Admin();
            Thread user = new User();
            user.setName("UserThread");
            admin.setName("AdminThread");
            System.out.println("Fetching details......");
            Thread.sleep(300);
            admin.start();
            System.out.println("Gathering results....");
            user.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
