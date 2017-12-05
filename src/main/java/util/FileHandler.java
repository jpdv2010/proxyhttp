package util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    //   private File folder = new File("/home/joao/Documents/Projects/proxyhttp/tmp/logs/");
//    private File file;

    private PrintWriter writer;
    private File file;
    private String fileName;
    private File directory = new File("/home/joao/Documents/Projects/proxyhttp/tmp/logs/");
    private DailyReport dailyReport;

    public FileHandler() throws FileNotFoundException, UnsupportedEncodingException {
        fileName = "log_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date())/*new Date() */ + ".txt";
        dailyReport = new DailyReport();
    }

    public void writeOnFile() throws FileNotFoundException, UnsupportedEncodingException {
        file = existsFile();
        writer = new PrintWriter("/home/joao/Documents/Projects/proxyhttp/tmp/logs/" + file.getName(), "UTF-8");
        for (Log l : dailyReport.getLogList()) {
            if (l.getRequestTime() != null) {
                writer.println("Requisiçao " + l.getMethod()
                        + " em: " + String.valueOf(l.getInputDate())
                        + " terminou: " + String.valueOf(l.getOutputDate())
                        + " - No Tempo: " + String.valueOf(l.getRequestTime()) + " ms");
                System.out.println("Requisiçao em: " + String.valueOf(l.getOutputDate()) + " - No Tempo: " + String.valueOf(l.getRequestTime()) + "ms");
            }
        }
        dailyReport.writeOnFile();
        writer.close();
    }

    private File existsFile() {
        File[] allFiles = directory.listFiles();
        if (allFiles != null) {
            for (File f : allFiles) {
                if (f.getName().equals(fileName)) {
                    return f;
                }
            }
        }
        dailyReport = new DailyReport();
        return new File(fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public List<Log> getDailyReport() {
        return dailyReport.getLogList();
    }

    public void setDailyReport(List<Log> logList) {
        this.dailyReport.setLogList(logList);
    }

}
