package com.pmp.log_manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GetDirectory {
    static File allFiles[];
    static String dir_path;

    synchronized static public File[] getAllFiles(String path) {
        File file = new File(path);
        if (file.isDirectory() && file.exists()) {
            allFiles = file.listFiles();
            dir_path = path;
           putInfoFile("[\" + Thread.currentThread().getName() + \"][\" + Thread.currentThread().getId() + \"][\" + Startup.date.toString() + \"][\" + getClass().getSimpleName() + \"] :Files Fetched");
        }
        return allFiles;
    }

    synchronized static public void printAllFiles() {
        int count = 0;
        System.out.println("Files present in the path " + dir_path + " are : ");
        for (File i : allFiles) {
            System.out.println("Index : " + count + " Name : " + i.getName());
            count++;
        }
    }
    static void putInfoFile(String info){
        File log = new File("./logs/Admin_log.txt");
        FileWriter fw;
        BufferedWriter write;
        try {
            fw = new FileWriter(log.getAbsoluteFile(), true);
            write=new BufferedWriter(fw);
            write.write(info);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
