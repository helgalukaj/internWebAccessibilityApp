package com.accessibilitychecker.webapp.report;

import java.util.List;

public class ReportResult {

    private List<ReportElement> reportElements;
    private int numberOfErrors;
    private int numberOfWarnings;
    private int numberOfNotices;

    public ReportResult(List<ReportElement> reportElements, int numberOfErrors, int numberOfWarning, int numberOfNotice) {
        this.reportElements = reportElements;
        this.numberOfErrors = numberOfErrors;
        this.numberOfWarnings = numberOfWarning;
        this.numberOfNotices = numberOfNotice;
    }

    public List<ReportElement> getReportElements() {
        return reportElements;
    }

    public void setReportElements(List<ReportElement> reportElements) {
        this.reportElements = reportElements;
    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }

    public void setNumberOfErrors(int numberOfErrors) {
        this.numberOfErrors = numberOfErrors;
    }

    public int getNumberOfWarnings() {
        return numberOfWarnings;
    }

    public void setNumberOfWarnings(int numberOfWarning) {
        this.numberOfWarnings = numberOfWarning;
    }

    public int getNumberOfNotices() {
        return numberOfNotices;
    }

    public void setNumberOfNotices(int numberOfNotice) {
        this.numberOfNotices = numberOfNotice;
    }
}
