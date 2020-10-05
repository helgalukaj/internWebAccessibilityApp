package com.accessibilitychecker.webapp.path;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Path {

    public List<PathElement> getPathList(String path){
        List<PathElement> pathList = new ArrayList<PathElement>();

        String[] pathArr = path.split(">");

        int length = pathArr.length;
        int index = 0;
        for(int i = 0; i < length;i++){
            if(i == 0){
                pathArr[i] = pathArr[i].substring(0,pathArr[i].length()-1);
                index = this.getChildIndex(pathArr[i]);
                if(index > 0){
                    pathArr[i] = pathArr[i].substring(0,pathArr[i].indexOf(':'));
                }
                pathList.add(new PathElement(pathArr[i],index));
            } else if(i < length-1 ) {
                pathArr[i] = pathArr[i].substring(1,pathArr[i].length()-1);
                index = this.getChildIndex(pathArr[i]);
                if(index > 0){
                    pathArr[i] = pathArr[i].substring(0,pathArr[i].indexOf(':'));
                }
                pathList.add(new PathElement(pathArr[i],index));
            } else {
                pathArr[i] = pathArr[i].substring(1,pathArr[i].length());
                index = this.getChildIndex(pathArr[i]);
                if(index > 0){
                    pathArr[i] = pathArr[i].substring(0,pathArr[i].indexOf(':'));
                }
                pathList.add(new PathElement(pathArr[i],index));
            }
        }

        for(PathElement element : pathList){
            System.out.println(element.getTagName() + " " + element.getNthChild());
        }

        return pathList;
    }

    private int getChildIndex(String pathElement){
        int index = 0;
        String intValue = new String();
        if(pathElement.contains("nth-child")){
            intValue = pathElement.replaceAll("[^0-9]", "");
            index = Integer.parseInt(intValue);
        }
        return index;
    }

    public String getEditedPath(String path){
        return path.replaceAll("nth-child","eq");
    }
}
