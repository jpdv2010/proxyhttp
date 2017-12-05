package util;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimerHandler {
    private Timer timer;
    private FileHandler fileHandler;
    private List<Log> logList;

    public TimerHandler() throws FileNotFoundException, UnsupportedEncodingException {
        fileHandler = new FileHandler();
        //if(existsArchive()) reuseLog(fileHandler.getFileName());
        this.logList = fileHandler.getDailyReport();
        start();
    }

    private void start() {
        this.timer = new Timer(5000, manageFile);//---------------Timer de 5 segundos pra nao dar excessao de modificaçao de Array
        this.timer.start();
    }

    private ActionListener manageFile = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Log l = new Log();
            l.setOutputDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
            if (Objects.equals(logList.size(), 0)) logList.add(0, l);
            if (!existsArchive()) {
                logList = new ArrayList<>();
            } else {
                fileHandler.setDailyReport(logList);
            }
            try {
                fileHandler.writeOnFile();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };

    private boolean existsArchive() {
        return fileHandler.getDailyReport().get(0).getOutputDate()
                .equals(String.valueOf(new SimpleDateFormat("dd-MM-yyyy")));
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }
}
