package com.pmp.log_manager;

import java.io.*;
import java.util.regex.Pattern;

public class SearchInFile extends Thread {
    File file;
    String key;
    Pattern pattern;
    Pattern pattern1;

    SearchInFile(File file) {
        this.file = file;
        key = "exception";
        pattern = Pattern.compile(PatternStatic.pattern);
        pattern1 = Pattern.compile(PatternStatic.pattern1);
    }

    public void run() {
        try (FileWriter fw = new FileWriter(Startup.log.getAbsoluteFile(), true); BufferedWriter write = new BufferedWriter(fw)) {
            write.write("[" + Thread.currentThread().getName() + "][" + Thread.currentThread().getId() + "][" + Startup.date.toString() + "][" + getClass().getSimpleName() + "] : Started");
            write.newLine();
            searchWholeFile(file, key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------------\\
    //---------------------------------------Main Logic Searching The File--------------------------------------------\\
    //----------------------------------------------------------------------------------------------------------------\\
    public void searchWholeFile(File file, String key) throws IOException {
        try (FileReader readcon = new FileReader(file);
             BufferedReader reader = new BufferedReader(readcon)) // Try with resource explanation.
        {
            boolean flag = false;
            String line, line1 = "", line2, trace;

            while ((line = reader.readLine()) != null) {
                flag = false;
                String tmp = pattern1.matcher(line).replaceAll("");//patter1->replaces the [.*Exception*] to empty string and store in tmp;
                if (tmp.toLowerCase().contains(key)) {
                    //checks for line has exception keyword or not.
                    trace = "";
                    flag = true;
                    //------------ traverse the stack trace if present. the loop breaks when the pattern is found-----\\
                    while ((line2 = reader.readLine()) != null) {
                        if (!pattern.matcher(line2).find() || ((line2.contains("at")) || (line2.contains("Caused")))) {
                            trace += ("&8&" + line2);

                        } else
                            break;

                    }

                    if (line1.equals("")) {
                        line1 = line;
                    }

                    if (flag) {
                        Save_Details.processLine(line1, line, currentThread().getName(), trace);
                    }

                }
                line1 = line; //stores the previous line
            }
        } catch (Exception e) {
            System.out.println("While Printing the whole file in SearchInFile.java " + e);
            e.printStackTrace();
        }
    }

}
