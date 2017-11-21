package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private String logDate;

    private Long requestTime;

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }
}
