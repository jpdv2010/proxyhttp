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

    private double[] dailyServiceTax(){
        List<Log> logList = new ArrayList<>();
        try {
           logList = getLogsFromArchive(this.file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert logList != null;
        double inputTax;
        double outputTax;
        Integer[] contInputsPerMin = new Integer[1440];
        Integer[] contOutputPerMin = new Integer[1440];
        for(Log log : logList){
            String[] logDayInput = log.getInputDate().split(" ");
            String[] logHourInput = logDayInput[2].split(":");
            String[] logDayOutput = log.getInputDate().split(" ");
            String[] logHourOutput = logDayInput[2].split(":");
            contInputsPerMin = requestsPerMinute(log.getInputDate(),logList,logHourInput);
            contOutputPerMin = requestsPerMinute(log.getOutputDate(),logList,logHourOutput);
        }
        int withRequestMinuteInput = 0;
        for(Integer minute:contInputsPerMin){
            if(minute != 0){
                withRequestMinuteInput++;
            }
        }

        int withRequestMinuteOutput = 0;
        for(Integer minute:contOutputPerMin){
            if(minute != 0){
                withRequestMinuteOutput++;
            }
        }

        double[] results = new double[2];
        results[0] = logList.size()/withRequestMinuteInput;
        results[1] = logList.size()/withRequestMinuteOutput;

        return results;
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
        double[] inputOutputTaxes = dailyServiceTax();
            writer.println("Taxa de chegada = " + inputOutputTaxes[0]);
            writer.println("Taxa de saida = " + inputOutputTaxes[1]);

        writer.println("Probabilidades do numero de cliente ser maior que 250" + probabilityClientNumberMajorN(250));
        writer.println("Probabilidades do numero de cliente ser maior que 400" + probabilityClientNumberMajorN(400));
        writer.println("Probabilidades do numero de cliente ser maior que 550" + probabilityClientNumberMajorN(550));
        writer.println("Probabilidades de haver clientes no sistema" + probabilityClientOnSystemN());
        writer.println("Numero medio de clientes na fila" + mediumNumberClientsOnQuery());
        writer.println("Tempo medio de espera na fila por cliente" + mediumQueryTime());
        writer.println("Tempo medio por cliente" + perClientMediumTime());

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

    private double probabilityClientOnSystemN(){
        file = existsFile();
        double serviceTax = dalilyMediumAccessTime();
        double[] inputOutputRage = dailyServiceTax();
        double probability = (serviceTax - 2 / serviceTax);
        return probability;
    }

    private double probabilityClientNumberMajorN(int value){
        file = existsFile();
        double serviceTax = dalilyMediumAccessTime();
        double[] inputOutputRage = dailyServiceTax();
        double probability = Math.pow(inputOutputRage[0],value) * (serviceTax - inputOutputRage[0]/inputOutputRage[0]);
        return probability;
    }

    private double probabilitySystemHaveBusy(){
        file = existsFile();
        double serviceTax = dalilyMediumAccessTime();
        double[] inputOutputRage = dailyServiceTax();
        double probability = inputOutputRage[0]/serviceTax;
        return probability;
    }

    private double mediumNumberClientsOnQuery(){
        file = existsFile();
        double serviceTax = dalilyMediumAccessTime();
        double[] inputOutputRage = dailyServiceTax();
        double probability = Math.pow(inputOutputRage[0],2)/serviceTax*(serviceTax-2);
        return probability;
    }

    private double mediumQueryTime(){
        file = existsFile();
        double serviceTax = dalilyMediumAccessTime();
        double[] inputOutputRage = dailyServiceTax();
        double probability = inputOutputRage[0]/serviceTax*(serviceTax-2);
        return probability;
    }

    private double perClientMediumTime(){
        file = existsFile();
        double serviceTax = dalilyMediumAccessTime();
        double[] inputOutputRage = dailyServiceTax();
        double probability = inputOutputRage[0]/serviceTax-2;
        return probability;
    }


    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }


}
