package com.accessibilitychecker.webapp.controller;

import com.accessibilitychecker.webapp.api.WebPage;
import com.accessibilitychecker.webapp.path.Path;
import com.accessibilitychecker.webapp.path.PathElement;
import com.accessibilitychecker.webapp.report.Report;
import com.accessibilitychecker.webapp.report.ReportElement;
import com.accessibilitychecker.webapp.report.ReportResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
class SampleController {

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("webPage", new WebPage());
        return "index";
    }


    @PostMapping("/result")
    public String result(@ModelAttribute WebPage webPage,
                         Model model) throws IOException {

        Document document = null;
        try {
            document = Jsoup.connect(webPage.getUrl()).get();
        } catch (Exception e){
            System.err.println("Error in given url!");
        }
//        Document doc = Jsoup.connect("https://www.google.com/").get();
        String pageHtml = "";
        if (document != null){
            pageHtml = document.outerHtml();
        } else {
            pageHtml = "Error in given url!";
        }

        Document docHtml = Jsoup.parse(pageHtml);

        ReportResult reportResult = Report.getReportElements(webPage.getUrl());

        List<ReportElement> reportElements = reportResult.getReportElements();

        // add icons
        for ( ReportElement reportElement : reportElements) {

            String context = reportElement.getContext();

//============================  HTML PARSING FROM PATH ====================================
//            String path = reportElement.getPath();
//            List<PathElement> pathList = new Path().getPathList(path);
//
//            Elements select = null;
//            for(PathElement pathElement: pathList){
//                if(pathElement.getNthChild()>0){
//                    select = docHtml.select(pathElement.getTagName()+":eq("+String.valueOf(pathElement.getNthChild())+")");
//                } else {
//                    select = docHtml.select(pathElement.getTagName()); //"div:eq(3)"
//                }
//            }
//            System.out.println(select.html());
//
//            String editedPath = new Path().getEditedPath(path);
//            select = docHtml.select(editedPath);
//            System.out.println(select.html());
//            System.out.println(context);
//            System.out.println(path);
//=======================================================================================
            if(pageHtml.contains(context)){
                switch(reportElement.getType())
                {
                    case "Error":
                        System.out.println("error");
                        pageHtml = pageHtml.replaceFirst(context,"<div class=\"tooltip\"><img  src=\"errorIcon.png\" alt=\"Error\" /><span class=\"tooltiptext\">"+reportElement.getDescription()+"</span></div>" + context );
                        break;
                    case "Warning":
//                        System.out.println("warning");
                        pageHtml = pageHtml.replaceFirst(context,"<img src=\"warningIcon.png\" alt=\"Warning\"/>" + context);
                        break;
                    case "Notice":
//                        System.out.println("notice");
                        pageHtml = pageHtml.replaceFirst(context,"<img src=\"noticeIcon.png\" alt=\"Notice\"/>" + context);
                        break;
                    default:
                        System.out.println("no match");
                }
            }
        }
        System.out.println("Errors: " + reportResult.getNumberOfErrors());
        System.out.println("Warnings: " + reportResult.getNumberOfWarnings());
        System.out.println("Notices: " + reportResult.getNumberOfNotices());
//        model.addAttribute("content", "<div class=\"tooltip\"><img  src=\"errorIcon.png\" alt=\"Error\" /><span class=\"tooltiptext\">tooltip text</span></div>");
//        model.addAttribute("content", "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<style>\n" +
//                ".tooltip {\n" +
//                "  position: relative;\n" +
//                "  display: inline-block;\n" +
//                "  border-bottom: 1px dotted black;\n" +
//                "}\n" +
//                "\n" +
//                ".tooltip .tooltiptext {\n" +
//                "  visibility: hidden;\n" +
//                "  width: 120px;\n" +
//                "  background-color: black;\n" +
//                "  color: #fff;\n" +
//                "  text-align: center;\n" +
//                "  border-radius: 6px;\n" +
//                "  padding: 5px 0;\n" +
//                "  \n" +
//                "  /* Position the tooltip */\n" +
//                "  position: absolute;\n" +
//                "  z-index: 1;\n" +
//                "  top: 100%;\n" +
//                "  left: 50%;\n" +
//                "  margin-left: -60px;\n" +
//                "}\n" +
//                "\n" +
//                ".tooltip:hover .tooltiptext {\n" +
//                "  visibility: visible;\n" +
//                "}\n" +
//                "</style>\n" +
//                "<body style=\"text-align:center;\">\n" +
//                "\n" +
//                "<h2>Bottom Tooltip</h2>\n" +
//                "<p>Move the mouse over the text below:</p>\n" +
//                "\n" +
//                "<div class=\"tooltip\"><img  src=\"errorIcon.png\" alt=\"Error\" /><span class=\"tooltiptext\">tooltip text</span></div>" +
//                "\n" +
//                "</body>\n" +
//                "</html>");
        model.addAttribute("content",pageHtml);
        model.addAttribute("numberOfErrors",Integer.toString(reportResult.getNumberOfErrors()));
        model.addAttribute("numberOfWarnings",Integer.toString(reportResult.getNumberOfWarnings()));
        model.addAttribute("numberOfNotices",Integer.toString(reportResult.getNumberOfNotices()));
        return "result";
    }
}
