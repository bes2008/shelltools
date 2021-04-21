package com.jn.shelltools.core.pypi.model.summary;

public class PipPackageDownload {
    private int last_day;
    private int last_month;
    private int last_week;

    public int getLast_day() {
        return last_day;
    }

    public void setLast_day(int last_day) {
        this.last_day = last_day;
    }

    public int getLast_month() {
        return last_month;
    }

    public void setLast_month(int last_month) {
        this.last_month = last_month;
    }

    public int getLast_week() {
        return last_week;
    }

    public void setLast_week(int last_week) {
        this.last_week = last_week;
    }
}
