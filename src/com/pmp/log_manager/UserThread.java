package com.pmp.log_manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.Scanner;

public class UserThread extends Thread {
    Scanner scanner = new Scanner(new FilterInputStream(System.in) {
        @Override
        public void close() throws IOException {
        }
    });
    String username, exceptionname, filename;

    boolean innerflag = true;

    UserThread(String name) {
        username = name;
    }

    public void run() {
        try (FileWriter fw = new FileWriter(Startup.userlog.getAbsoluteFile(), true); BufferedWriter write = new BufferedWriter(fw)) {
            write.write("[" + Thread.currentThread().getName() + "][" + Thread.currentThread().getId() + "][" + Startup.date.toString() + "][" + getClass().getSimpleName() + "] : Started");
            write.newLine();
            write.newLine();
            int ch1, count1 = 0;
            write.write("\tUser Name :" + username + " Time : [" + Startup.date.toString() + "]");
            write.newLine();
            while (innerflag) {
                count1++;
                User.searchcount++;
                exceptionname = "";
                filename = "";
                typeWriterEffect("Hello " + username + ", choose your way of search", (byte) 1);
                typeWriterEffect("1 ) Search an Exception in all Files", (byte) 0);
                typeWriterEffect("2 ) Search All Exceptions in a File", (byte) 0);
                typeWriterEffect("Display All exception in All Files 3->Only Name 4->Stack Trace also", (byte) 0);
                typehere(username);
                new Refresh(GetDirectory.allFiles);
                ch1 = scanner.nextInt();

                while (ch1 > 4 && ch1 < 1) {
                    typeWriterEffect("Irrelevant Option Try Again", (byte) 1);
                    typehere(username);
                    ch1 = scanner.nextInt();
                }
                write.write("\t Id :" + count1 + "Search Option : " + ch1 + " Time : [" + Startup.date.toString() + "]");
                write.newLine();
                switch (ch1) {
                    case 1:
                        typeWriterEffect(username + " can you mention the exception name ?", (byte) 1);
                        typehere(username);
                        exceptionname = scanner.next().toLowerCase();
                        write.write("\t\t Search Key : " + exceptionname + " Time : [" + Startup.date.toString() + "]");

                        if (!exceptionname.contains("exception")) {
                            exceptionname += "exception";
                        }
                        typeWriterEffect(username + " processing your query", (byte) 1);
                        typeWriterEffect("Hey " + username + " do want their stack trace? 1->yes 2->No", (byte) 1);
                        typehere(username);
                        int op = scanner.nextInt();
                        write.write(" " + op);
                        write.newLine();
                        typeWriterEffect("  Fetching Data . . . . . . .", (byte) 1);
                        System.out.println("\n");
                        System.out.println("----------------------------------------------------------------------------\n");
                        DataStore.displayData_FilesBasedOnException(exceptionname, op);
                        System.out.println("----------------------------------------------------------------------------\n");
                        System.out.println("\n");
                        typeWriterEffect(username + " hope you had your result :)", (byte) 1);
                        break;
                    case 2:
                        typeWriterEffect("Shall I display the available file names? yes-1 no-2", (byte) 1);
                        typehere(username);
                        op = scanner.nextInt();
                        write.write("\t\t " + op);
                        if (op == 1) {
                            GetDirectory.printAllFiles();
                        }
                        typeWriterEffect(username + " Can you mention the File name ?", (byte) 1);
                        typeWriterEffect("Enter Your File Name", (byte) 1);
                        typehere(username);
                        filename = scanner.next().toLowerCase();
                        write.write(" SearchKey : " + filename + " Time : " + Startup.date.toString() + "]");
                        typeWriterEffect(username + " processing your query", (byte) 1);
                        typeWriterEffect("Hey " + username + " do want their stack trace? 1->yes 2->No", (byte) 1);
                        typehere(username);
                        op = scanner.nextInt();
                        write.write(" " + op);
                        write.newLine();
                        typeWriterEffect("  Fetching Data . . . . . . .", (byte) 1);
                        System.out.println("\n");
                        System.out.println("----------------------------------------------------------------------------\n");
                        DataStore.displayData_AllExceptionInAFile(filename, op);
                        System.out.println("----------------------------------------------------------------------------\n");
                        System.out.println("\n");
                        typeWriterEffect(username + " hope you had your result :)", (byte) 1);
                        break;
                    case 3:
                        typeWriterEffect("Printing Exception name and their file names", (byte) 1);
                        System.out.println("----------------------------------------------------------------------------\n");
                        DataStore.displayData_FilesAndException();
                        System.out.println("----------------------------------------------------------------------------\n");
                        typeWriterEffect(username + " hope you had your result :)", (byte) 1);
                        break;
                    case 4:
                        typeWriterEffect("The All Info Is gets Printed ", (byte) 1);
                        System.out.println("----------------------------------------------------------------------------\n");
                        DataStore.displayData_All();
                        System.out.println("----------------------------------------------------------------------------\n");
                        typeWriterEffect(username + " hope you had your result :)", (byte) 1);
                        break;
                    default:
                        write.write("User Option is Not Valid One");
                        write.newLine();
                        typeWriterEffect("Your Option Is Invalid " + username, (byte) 1);
                        break;

                }
                typeWriterEffect("hey, " + username + " Do you want to search again?", (byte) 1);
                typeWriterEffect("Yes-> hit 1 :) No -> hit 2 :(", (byte) 0);
                typehere(username);
                int op = scanner.nextInt();
                if (op == 1) {
                    innerflag = true;
                } else {
                    typeWriterEffect("Logged Out Sucessfully.......", (byte) 1);
                    typeWriterEffect("This Was developed by Somes Kumar K. :!", (byte) 1);
                    typeWriterEffect("Thank You :)" + username, (byte) 0);
                    innerflag = false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
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
