package com.accessibilitychecker.webapp.report;

public class ReportElement {

    private String type;
    private String description;
    private String path;
    private String context;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ReportElement(String type, String context) {
        this.type = type;
        this.context = context;
    }

    public ReportElement() {

    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }


}