package edu.tcu.cs.superfrogscheduler.reports.dto;

import java.util.List;

/**
 * A data transfer object used to encapsulate a list of selected request ids and the payment period.
 */
public class RequestIds {

    private List<String> requestIds;

    private Period period;


    public RequestIds() {

    }

    public RequestIds(List<String> requestIds, Period period) {
        this.requestIds = requestIds;
        this.period = period;
    }

    public List<String> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<String> requestIds) {
        this.requestIds = requestIds;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
