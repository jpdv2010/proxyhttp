package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyReport {
    private List<Log> logList = new ArrayList<>();
    private PrintWriter writer;
    private File file;
    private String fileName;
    private File directory = new File("/home/joao/Documents/Projects/proxyhttp/tmp/logs/");



    public DailyReport(){
        fileName = "daily_log:" +new SimpleDateFormat("dd-MM-yyyy").format(new Date())/*new Date() */+ ".txt";
    }


    private double dalilyMediumAccessTime(){
        Long fullAccessTime = 0L;
        for(Log l:logList){
            if(l.getRequestTime() != null){
                fullAccessTime += l.getRequestTime();
            }
        }
        return fullAccessTime/logList.size();
    }

    public void writeOnFile() throws FileNotFoundException, UnsupportedEncodingException {
        file = existsFile();
        writer = new PrintWriter("/home/joao/Documents/Projects/proxyhttp/tmp/logs/" + file.getName(), "UTF-8");
        writer.println("Tempo medio de execuçao em: "
                + new SimpleDateFormat("dd-MM-yyyy").format(new Date())
                + " foi de: " + dalilyMediumAccessTime());

        writer.close();
    }

    private File existsFile(){
        File[] allFiles = directory.listFiles();
        if(allFiles != null){
            for(File f : allFiles){
                if(f.getName().equals(fileName)) {
                    return f;
                }
            }
        }
        return new File(fileName);
    }


    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }


}
