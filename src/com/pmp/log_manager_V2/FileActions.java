package com.pmp.log_manager_V2;

import java.io.File;

public class FileActions extends Thread {
    File curfile;

    FileActions(ThreadGroup tgFiles, File i) {
        super(tgFiles, currentThread().getName());
        curfile = i;
    }

    synchronized public void run() {
        System.out.println("The File is \" " + curfile.getName() + " \" processing under thread -> " + currentThread().getName());
        Thread searchfile = new FileActions_Search(curfile, "exception");
        searchfile.start();
    }


}
