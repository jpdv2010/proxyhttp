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
    private File directory = new File("/home/joao/Documents/Projects/proxyhttp/tmp/logs/");
    private List<Log> logList;

    public FileHandler() throws FileNotFoundException, UnsupportedEncodingException {
        fileName = "log_" +new SimpleDateFormat("dd-MM-yyyy").format(new Date())/*new Date() */+ ".txt";
        logList = new ArrayList<>();
    }

    public void writeOnFile() throws FileNotFoundException, UnsupportedEncodingException {
        file = existsFile();
        writer = new PrintWriter("/home/joao/Documents/Projects/proxyhttp/tmp/logs/" + fileName, "UTF-8");
        for(Log l : logList){
            if(l.getRequestTime() != null){
                writer.println("Requisiçao em: " + String.valueOf(l.getLogDate()) + " - No Tempo: " + String.valueOf(l.getRequestTime()) + "ms");
                System.out.println("Requisiçao em: " + String.valueOf(l.getLogDate()) + " - No Tempo: " + String.valueOf(l.getRequestTime()) + "ms");
            }
        }
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
      logList = new ArrayList<>();
      return new File(fileName);
  }


    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

}
