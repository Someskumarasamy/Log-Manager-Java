package com.pmp.log_manager;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Save_Details {

    synchronized static void processLine(String firstLine, String data, String tname, String trace) {
        Pattern excname, excclass, excdate, exctime;
        String classname = "NOTFOUND", date = "NOTFOUND", time = "NOTFOUND"; //defaultValues;

        try (FileWriter fw = new FileWriter(Startup.log.getAbsoluteFile(), true); BufferedWriter write = new BufferedWriter(fw)) {
            excname = Pattern.compile("([a-zA-z]*Exception)");
            excclass = Pattern.compile("([A-Za-z]+\\.)+([A-Za-z]+)");
            excdate = Pattern.compile("(\\[(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])-([0-9]{4})\\])");
            exctime = Pattern.compile("(\\[([0-9]{2}):([0-9]{2}):([0-9]{2}):([0-9]{3})\\])");
            Matcher matchname = excname.matcher(data);
            Matcher matchclass = excclass.matcher(firstLine);
            Matcher matchdate = excdate.matcher(firstLine);
            Matcher matchtime = exctime.matcher(firstLine);

            if (matchclass.find()) {
                classname = matchclass.group().toLowerCase();
            }
            if (matchdate.find()) {
                date = matchdate.group().toLowerCase();
            }
            if (matchtime.find()) {
                time = matchtime.group().toLowerCase();
            }
            if (matchname.find()) {
                data = matchname.group(1).toLowerCase();
                write.write("[" + Thread.currentThread().getName() + "][" + Thread.currentThread().getId() + "][" + Startup.date.toString() + "][" + Startup.class.getSimpleName() + "] : Adding Data");
                write.newLine();
                DataStore.addData(data, tname, trace, classname, date, time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Note : Here all details are converted into lower case, to make easy fetching from data store.
