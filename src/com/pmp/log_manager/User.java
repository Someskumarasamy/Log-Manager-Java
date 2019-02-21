package com.pmp.log_manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class User extends Thread {

    static int usercount = 0, searchcount = 0, admincount = 0;
    String username, exceptionname, filename;
    int ch1;
    boolean innerflag = true, outerflag = true;

    public void run() {
        try (Scanner scanner = new Scanner(System.in); FileWriter fw = new FileWriter(Startup.userlog.getAbsoluteFile(), true); BufferedWriter write = new BufferedWriter(fw)) {

            while (outerflag) {
                write.write("[" + Thread.currentThread().getName() + "][" + Thread.currentThread().getId() + "][" + Startup.date.toString() + "][" + getClass().getSimpleName() + "] : Started");
                write.newLine();
                System.out.println("\n\n\n");
                username = "user";
                typeWriterEffect("Welcome User I am Your Exception Finder", (byte) 1);
                typeWriterEffect("enter your name to proceed", (byte) 1);
                typehere(username);
                if (scanner.hasNext())
                    username = scanner.next();
                else
                    username = "default";
                if (username.equals("default")) {
                    username = scanner.next();
                }
                if (username.equals("Admin")) {

                    write.newLine();
                    write.write("\tUser Name :" + username + " Time : [" + Startup.date.toString() + "] ");
                    typeWriterEffect("enter your password", (byte) 1);
                    typehere(username);
                    username = scanner.next();
                    if (username.equals("password")) {
                        admincount++;
                        write.write("Admin Count : " + admincount + " : [" + Startup.date.toString() + "]");
                        write.newLine();
                        typeWriterEffect("Do You Want to Kill This User Thread ? 1->Kill 2->ReLoad 3->Check-Status 4->Exit", (byte) 1);
                        typehere("Admin");
                        ch1 = scanner.nextInt();
                        if (ch1 == 1) {
                            write.write("\t\t[" + Startup.date.toString() + "]Killed The App.");
                            write.newLine();
                            outerflag = false;
                            innerflag = false;
                            continue;
                        } else if (ch1 == 2) {
                            write.write("\t\t[" + Startup.date.toString() + "]Refreshed the Files.");
                            write.newLine();
                            typeWriterEffect("All Existing Data will be removed!", (byte) 1);
                            new Refresh(GetDirectory.allFiles);
                            typeWriterEffect("Files are refreshed....", (byte) 1);
                            continue;
                        } else if (ch1 == 3) {
                            write.write("\t\t[" + Startup.date.toString() + "]Checked Status.");
                            write.newLine();
                            typeWriterEffect("Gathering From Your Data....", (byte) 1);
                            typeWriterEffect("User Count " + User.usercount, (byte) 1);
                            typeWriterEffect("Total Searches " + User.searchcount, (byte) 0);
                            continue;
                        } else {
                            write.write("\t\t[" + Startup.date.toString() + "]Admin Done Nothing.");
                            write.newLine();
                            typeWriterEffect("Logged Out Successfully.......", (byte) 1);
                            continue;
                        }
                    } else {
                        write.write("\t\t[" + Startup.date.toString() + "]Password Authentication Failure.");
                        write.newLine();
                        typeWriterEffect("Sorry Authentication Failed..", (byte) 1);
                        continue;
                    }
                }
                usercount++;
                write.newLine();
                Thread ut = new UserThread(username);
                ut.start();
                ut.setName(username);
                ut.join();
                write.write("User :" + username + " Id : " + usercount + " Ended Session Time : [" + Startup.date.toString() + "]");
                write.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void typeWriterEffect(String s, byte x) throws InterruptedException {
        char arr[] = s.toCharArray();
        if (x == 1) System.out.print("Bot says : ");
        else System.out.print("           ");
        for (char i : arr) {
            Thread.sleep(10);
            System.out.print(i);
            Thread.sleep(10);
        }
        System.out.println();
    }

    public void typehere(String name) {
        System.out.print("\t\t\t\t\t\t\t" + name + " says : ");
    }
}
