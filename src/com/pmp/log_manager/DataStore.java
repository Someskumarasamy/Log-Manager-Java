package com.pmp.log_manager;

import java.util.*;

class TraceDetails {
    String time, date, classname, trace;

    TraceDetails(String time, String date, String className, String trace) {
        this.time = time;
        this.date = date;
        this.classname = className;
        this.trace = trace;

    }
}

class DataStore {
    static HashMap<String, Long> filehashvalues = new HashMap<String, Long>();
    private static LinkedHashMap<String, LinkedHashMap<String, ArrayList<TraceDetails>>> datastore = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<TraceDetails>>>();

    synchronized static void addData(String excname, String fname, String trace, String classname, String date, String time) {
        if (datastore.containsKey(excname)) {
            if (!datastore.get(excname).containsKey(fname)) {
                datastore.get(excname).put(fname, (new ArrayList()));
            }
            datastore.get(excname).get(fname).add(new TraceDetails(time, date, classname, trace));
        } else {
            datastore.put(excname, new LinkedHashMap<>());
            datastore.get(excname).put(fname, new ArrayList<>());
            datastore.get(excname).get(fname).add(new TraceDetails(time, date, classname, trace));
        }
    }

    synchronized static void displayData_All() {
        for (Map.Entry<String, LinkedHashMap<String, ArrayList<TraceDetails>>> i : datastore.entrySet()) {
            System.out.println("Exception name --> " + i.getKey());
            for (Map.Entry<String, ArrayList<TraceDetails>> j : i.getValue().entrySet()) {
                System.out.println("\nFile Name :" + j.getKey());
                Iterator<TraceDetails> itr = j.getValue().iterator();
                while (itr.hasNext()) {
                    TraceDetails td = itr.next();
                    System.out.println("Class Name : " + td.classname + "\nTime : " + td.time + "\nDate : " + td.date);
                    String arr[] = td.trace.split("&8&");
                    for (String tt : arr) {
                        System.out.println("\t\t" + tt);
                    }
                }

            }

        }
    }

    static void displayData_FilesAndException() {
        for (Map.Entry<String, LinkedHashMap<String, ArrayList<TraceDetails>>> i : datastore.entrySet()) {
            System.out.println("Exception name --> " + i.getKey());
            for (Map.Entry<String, ArrayList<TraceDetails>> j : i.getValue().entrySet()) {
                System.out.println("\t\t:" + j.getKey());
            }
        }

    }

    synchronized static void displayData_FilesBasedOnException(String name, int op) {
        if (datastore.containsKey(name.toLowerCase())) {
            System.out.println("The Files throwing the " + name + " are :");
            HashMap<String, ArrayList<TraceDetails>> i = datastore.get(name);
            for (Map.Entry<String, ArrayList<TraceDetails>> j : i.entrySet()) {
                System.out.println(j.getKey());
                if (op == 1) {
                    Iterator<TraceDetails> itr = j.getValue().iterator();
                    while (itr.hasNext()) {
                        TraceDetails td = itr.next();
                        System.out.println("Class Name :" + td.classname + "\nTime : " + td.time + "\nDate : " + td.date);
                        String arr[] = td.trace.split("&8&");
                        for (String tt : arr) {
                            System.out.println("\t\t" + tt);
                        }
                    }
                }
            }
            i.clear();

        } else {
            System.out.println("\n\nNo Data Found for the search \n\n");
        }
    }

    synchronized static void displayData_AllExceptionInAFile(String name, int op) {

        boolean checkflag = false;
        for (Map.Entry<String, LinkedHashMap<String, ArrayList<TraceDetails>>> i : datastore.entrySet()) {
            HashMap<String, ArrayList<TraceDetails>> j = i.getValue();
            if (j.containsKey(name)) {
                checkflag = true;
                System.out.println(i.getKey());
                if (op == 1) {
                    Iterator<TraceDetails> itr = j.get(name).iterator();
                    while (itr.hasNext()) {
                        TraceDetails td = itr.next();
                        System.out.println("\nClass Name : " + td.classname + "\nTime : " + td.time + "\nDate : " + td.date);
                        String arr[] = td.trace.split("&8&");
                        for (String tt : arr) {
                            System.out.println("\t\t" + tt);
                        }
                    }

                }
            }

        }
        if (!checkflag) {
            System.out.println("\n\nNo Data Found for the search \n\n");
        }
    }

}




