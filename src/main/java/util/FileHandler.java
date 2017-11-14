package util;

import org.apache.tomcat.jni.Directory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileHandler {

 //   private File folder = new File("/home/joao/Documents/Projects/proxyhttp/tmp/logs/");
//    private File file;

    private PrintWriter writer;
    private File file;
    private String fileName;

    public FileHandler() throws FileNotFoundException, UnsupportedEncodingException {
        fileName = "file_" +new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date())/*new Date() */+ ".txt";

        writer = new PrintWriter("/home/joao/Documents/Projects/proxyhttp/tmp/logs/" + fileName, "UTF-8");
    }

    public void writeOnFile(List<String> logList) {
        file = new File(fileName);
        for(String s : logList){
            writer.println(s);
            System.out.println(s);
        }
        writer.close();
  }

 /*   public bool exists(String fileN) {

        String fileName = "app" + new Date().getTime() + ".log";

        if (folder.listFiles() != null)  {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.getName().equals(fileName)) {
                    file = fileEntry;
                    return ;
                } else {
                    file = new File(folder + fileName);
                }
            }
        } else {
            file = new File(folder + fileName);
        }
        //todo write
    }*/

}
