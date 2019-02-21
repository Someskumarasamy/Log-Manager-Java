package com.pmp.log_manager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Admin extends Thread {

    public void run() {
        try (FileWriter fw = new FileWriter(Startup.log.getAbsoluteFile(), true); BufferedWriter write = new BufferedWriter(fw)) {
            write.write("[" + Thread.currentThread().getName() + "][" + Thread.currentThread().getId() + "][" + Startup.date.toString() + "][" + getClass().getSimpleName() + "] : Started");
            write.newLine();
            GetDirectory gd = new GetDirectory();
            String dir = "/home/somes-pt2166/Documents/AdventNet/MickeyLite/logs";
            File dat[] = GetDirectory.getAllFiles(dir);
            write.write("[" + Thread.currentThread().getName() + "][" + Thread.currentThread().getId() + "][" + Startup.date.toString() + "][" + Startup.class.getSimpleName() + "] : Fetched Files.");
            write.newLine();
            Thread sik = new SearchInKey(dat);
            sik.setName("SearchInFileThread");
            sik.start();
            sik.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
