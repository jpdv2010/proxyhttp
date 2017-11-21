package util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimerHandler {
    private Timer timer;
    private FileHandler fileHandler;
    private List<Log> logList;

    public TimerHandler() throws FileNotFoundException, UnsupportedEncodingException {
        start();
        fileHandler = new FileHandler();
        this.logList = fileHandler.getLogList();
    }

    private void start(){
        this.timer = new Timer(1000,manageFile);
        this.timer.start();
    }

    private ActionListener manageFile = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Log l = new Log();
            l.setLogDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
            logList.add(0,l);
            if(fileHandler.getLogList().get(0).getLogDate().equals(String.valueOf(new SimpleDateFormat("dd-MM-yyyy")))){
                logList = new ArrayList<>();
            }else{
                fileHandler.setLogList(logList);
            }
            try {
                fileHandler.writeOnFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }
}