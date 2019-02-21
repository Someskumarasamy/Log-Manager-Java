package com.pmp.log_manager_V2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileActions_Search extends Thread {
    File file;
    String key;
    Pattern pattern;
    Pattern pattern1;

    FileActions_Search(File file, String key) {
        this.file = file;
        this.key = key.toLowerCase();
        pattern = Pattern.compile(PatternStatic.pattern);
        pattern1 = Pattern.compile(PatternStatic.pattern1);

    }

    public void run() {
        try {
            searchWholeFile(file, key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------------------\\
    //---------------------------------------Main Logic Searching The File-----------------------------------------\\
//----------------------------------------------------------------------------------------------------------------------\\
    synchronized public void searchWholeFile(File file, String key) throws IOException {
        BufferedReader reader = null;
        try {
            String line, line1 = "", line2;
            FileReader readcon = new FileReader(file);
            reader = new BufferedReader(readcon);
            while ((line = reader.readLine()) != null) {
                String tmp = pattern1.matcher(line).replaceAll("");
                if (tmp.toLowerCase().contains(key) && !(tmp.toLowerCase().contains("at") || tmp.toLowerCase().contains("caused"))) {

                    if (!line1.equals(""))
                        System.out.println(line1 + "\n" + line);
                    else
                        System.out.println(line);
                    Thread sst = new SearchStackTrace(reader, pattern);
                    sst.setName("SearchStackTrace");
                    sst.start();
                    sst.join();


                    line1 = "";
                    //Save_Details.processLine(line, currentThread().getName());
                }
                line1 = line;
            }

        } catch (Exception e) {
            System.out.println("While Printing the whole file in SearchInFile.java " + e);
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }
//----------------------------------------------------------------------------------------------------------------------\\
//----------------------------------------------------------------------------------------------------------------------\\
}

class SearchStackTrace extends Thread {
    BufferedReader reader;
    Pattern pattern;
    String trace = "";

    SearchStackTrace(BufferedReader reader, Pattern pattern) {
        this.pattern = pattern;
        this.reader = reader;
    }

    synchronized public void run() {
        //SearchingFunction();
    }

    synchronized String SearchingFunction() {
        try {
            String line2;
            while ((line2 = reader.readLine()) != null) {

                if (!pattern.matcher(line2).find() || line2.toLowerCase().contains("at") || line2.toLowerCase().contains("caused")) {
                    System.out.println("\t" + line2);
                    trace += "*&*" + line2;
                } else
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trace;
    }


}

