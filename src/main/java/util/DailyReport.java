package util;

import java.io.*;
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


    public DailyReport() {
        fileName = "daily_log:" + new SimpleDateFormat("dd-MM-yyyy").format(new Date())/*new Date() */ + ".txt";
        Log log = new Log();
        log.setOutputDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy")));
        logList.add(0, log);

    }


    private double dalilyMediumAccessTime() {
        Long fullAccessTime = 0L;
        for (Log l : logList) {
            if (l.getRequestTime() != null) {
                fullAccessTime += l.getRequestTime();
            }
        }
        return fullAccessTime / logList.size();
    }

    private double inputRage(){


        return 0.0;
    }

    private double dailyServiceTax(File file){
        List<Log> logList = new ArrayList<>();
        try {
           logList = getLogsFromArchive(file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert logList != null;
        double inputTax;
        double outputTax;
        Integer[] contInputsPerMin = new Integer[1440];
        Integer[] contOutputPerMin = new Integer[1440];
        for(Log log : logList){
            String[] logDay = log.getInputDate().split(" ");
            String[] logHour = logDay[2].split(":");
            contInputsPerMin = requestsPerMinute(log.getInputDate(),logList,logHour);
            contOutputPerMin = requestsPerMinute(log.getOutputDate(),logList,logHour);
        }
        int withRequestMinute = 0;
        for(Integer minute:contInputsPerMin){
            if(minute != 0){
                withRequestMinute++;
            }
        }
        return logList.size()/withRequestMinute;
    }

    private Integer[] requestsPerMinute(String logDate, List<Log> logList, String[] logHour){
        Integer[] contInputsPerMin = new Integer[1440];
        for(int i = 0;i < logList.size() ; i++){
            int minute = (Integer.parseInt(logHour[0])*60)+Integer.parseInt(logHour[1]);
            if(logDate.equals(logList.get(i).getInputDate()))
            {
                contInputsPerMin[minute]++;
            }
        }
        return contInputsPerMin;
    }

    private double dailyArrivalTax(){
        return 0;
    }

    public void writeOnFile() throws FileNotFoundException, UnsupportedEncodingException {
        file = existsFile();
        writer = new PrintWriter("/home/joao/Documents/Projects/proxyhttp/tmp/logs/" + file.getName(), "UTF-8");
        writer.println("Tempo medio de execuçao em: "
                + new SimpleDateFormat("dd-MM-yyyy").format(new Date())
                + " foi de: " + dalilyMediumAccessTime());

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
        return new File(fileName);
    }

    private List<Log> getLogsFromArchive(String fileName) throws IOException {
        List<Log> logs = new ArrayList<>();
        File file = existsFile();
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        int i=0;
        String line = reader.readLine();
        while (line != null) {
            String[] data = line.split(" ");
            Log log = new Log();
            log.setRequestTime(Long.parseLong(data[12]));
            log.setInputDate(data[4] + " " + data[5]);
            log.setOutputDate(data[7] + " " + data[8]);
            log.setMethod(data[2]);
            logs.add(log);
            i++;
        }
        if(i == 0){return null;}
        return logs;
    }


    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }


}
