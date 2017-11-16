package util;

import org.apache.tomcat.jni.Directory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileHandler {

 //   private File folder = new File("/home/joao/Documents/Projects/proxyhttp/tmp/logs/");
//    private File file;

    private PrintWriter writer;
    private File file;
    private String fileName;
    private Timer timer;
    private File directory = new File("/home/joao/Documents/Projects/proxyhttp/tmp/logs/");
    //public static boolean isStarted=false;
    private List<Long> logList = new ArrayList<>();

    public FileHandler() throws FileNotFoundException, UnsupportedEncodingException {
        fileName = "log_" +new SimpleDateFormat("dd-MM-yyyy").format(new Date())/*new Date() */+ ".txt";
        writer = new PrintWriter("/home/joao/Documents/Projects/proxyhttp/tmp/logs/" + fileName, "UTF-8");
        configTimer();
        this.timer.start();
    }

    public void writeOnFile() {
        File[] allFiles = directory.listFiles();
        if(allFiles != null){
            for(File f : allFiles){
                if(f.getName().equals(fileName)){
                    file = f;
                }else{
                    file = new File(fileName);
                }
            }
        }
        for(Long l : logList){
            writer.println(String.valueOf(l));
            System.out.println(String.valueOf(l));
        }
        writer.close();
  }

    private void configTimer(){
        this.timer = new Timer(1000,generateLog);
    }

    private ActionListener generateLog = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

                writeOnFile();

        }
    };

    public List<Long> getLogList() {
        return logList;
    }

    public void setLogList(List<Long> logList) {
        this.logList = logList;
    }


}
