package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private String logDate;

    private Long requestTime;

    private String method;

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
