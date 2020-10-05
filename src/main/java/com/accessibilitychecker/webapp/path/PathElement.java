package com.accessibilitychecker.webapp.path;

public class PathElement {
    private String tagName;
    int nthChild;

    public PathElement(String tagName, int nthChild) {
        this.tagName = tagName;
        this.nthChild = nthChild;
    }

    public int getNthChild() {
        return nthChild;
    }

    public void setNthChild(int nthChild) {
        this.nthChild = nthChild;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
