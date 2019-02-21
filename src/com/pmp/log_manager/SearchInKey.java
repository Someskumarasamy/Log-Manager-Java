package com.pmp.log_manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SearchInKey extends Thread {
    File[] data;

    SearchInKey(File[] data) {
        this.data = data;
    }

    public void run() {

        try (FileWriter fw = new FileWriter(Startup.log.getAbsoluteFile(), true); BufferedWriter write = new BufferedWriter(fw)) {
            write.write("[" + Thread.currentThread().getName() + "][" + Thread.currentThread().getId() + "][" + Startup.date.toString() + "][" + getClass().getSimpleName() + "] : Started");
            write.newLine();
            for (File index : data) {
                if (!DataStore.filehashvalues.containsKey(index.getName())) {
                    DataStore.filehashvalues.put(index.getName(), index.lastModified());
                    write.write("\t\t[" + getName() + "][" + Thread.currentThread().getId() + "]: Adding Hash Values :" + index.getName());
                    write.newLine();
                    Thread sif = new SearchInFile(index);
                    sif.setName(index.getName());
                    sif.start();
                } else {
                    if (!(DataStore.filehashvalues.get(index.getName()) == index.lastModified())) {
                        write.write("\t\t[" + getName() + "][" + Thread.currentThread().getId() + "]: Adding Hash Values IE FILE CHANGED:" + index.getName());
                        write.newLine();
                        DataStore.filehashvalues.put(index.getName(), index.lastModified());
                        Thread sif = new SearchInFile(index);
                        sif.setName(index.getName());
                        sif.start();
                    } else {
                        //System.out.println(index.getName()+" is Not Modified !");
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
