package com.accessibilitychecker.webapp.report;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Report {

    public static ReportResult getReportElements(String websiteUrl) throws IOException {

        List<ReportElement> reportElements = new ArrayList<>();
        int numberOfErrors = 0;
        int numberOfWarnings = 0;
        int numberOfNotices = 0;
        int lineCounter = 0;
        int counter = 0;

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", "pa11y " + websiteUrl);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF8"), 8)) {
            String line;
            ReportElement reportElement = new ReportElement();
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                lineCounter++;
                if (lineCounter > 7 && !line.equals("")) {
                    if (counter == 0 && line.length() > 10) {
                        String type = line.substring(3,line.indexOf(':'));
                        switch (type){
                            case "Error":
                                numberOfErrors++;
                                reportElement.setType(type);
                                break;
                            case "Warning":
                                numberOfWarnings++;
                                reportElement.setType(type);
                                break;
                            case "Notice":
                                numberOfNotices++;
                                reportElement.setType(type);
                                break;
                        }
                        counter = 1;
                        reportElement.setDescription(line.substring(line.indexOf(':')+2, line.length()));
                    } else if (counter == 1) {
                        //System.out.println(line.substring(7, line.length()));
                        counter = 2;
                    } else if (counter == 2) {
                        reportElement.setPath(line.substring(7, line.length()));
                        counter = 3;
                    } else if (counter == 3) {
                        reportElement.setContext(line.substring(7, line.length()));
//                        System.out.println(line.substring(7, line.length()));
                        boolean exists = false;
                        for (int i = 0; i < reportElements.size(); i++) {
                            if(reportElement.getContext().equals(reportElements.get(i).getContext()) ){
                                exists = true;
                            }
                        }
                        if(!exists){
                            reportElements.add(reportElement);
                        } else {
                            switch(reportElement.getType()) {
                                case "Error":
                                    numberOfErrors--;
                                    break;
                                case "Warning":
                                    numberOfWarnings--;
                                    break;
                                case "Notice":
                                    numberOfNotices--;
                                    break;
                            }
                        }
                        reportElement = new ReportElement();// to make each error seperate
                        counter = 0;
                    }
                }
            }
        }
        return new ReportResult(reportElements, numberOfErrors, numberOfWarnings, numberOfNotices);
    }
}
