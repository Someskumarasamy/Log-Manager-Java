package com.pmp.log_manager_V2;

public class Main {
    public static void main(String[] args) {
        try {
            Thread admin = new Admin();
            admin.setName("Admin");
            admin.start();
            admin.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Application is Ready for Usage\n welcome user...");
    }
}
