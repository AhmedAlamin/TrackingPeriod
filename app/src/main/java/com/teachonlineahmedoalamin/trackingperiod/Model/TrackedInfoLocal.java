package com.teachonlineahmedoalamin.trackingperiod.Model;

import java.util.Date;

public class TrackedInfoLocal {

    private int id , daysOfCycle,periodDays;
    private String lastDateLocal;
    private String localName;



    public int getId() {
        return id;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDaysOfCycle() {
        return daysOfCycle;
    }

    public void setDaysOfCycle(int daysOfCycle) {
        this.daysOfCycle = daysOfCycle;
    }

    public String getLastDateLocal() {
        return lastDateLocal;
    }

    public void setLastDateLocal(String lastDateLocal) {
        this.lastDateLocal = lastDateLocal;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }
}
