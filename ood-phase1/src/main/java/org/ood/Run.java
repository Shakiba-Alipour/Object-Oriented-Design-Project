package org.ood;

import java.io.IOException;

public class Run {
    public static void main(String[] args) throws IOException {

//        ProjectExtractor.extractProject("D:/OOD-Projects/Netbeans" , "NetBeans");
        ProjectExtractor.extractProject("D:\\University\\Object Oriented Design\\Project\\" +
                "Open Source OO project-20230329\\4 - Netbeans v1.0.x", "NetBeans");

//        ProjectExtractor.extractProject("D:/OOD-Projects/QuickUML/src" , "QuickUML");
//        ProjectExtractor.extractProject("D:/OOD-Projects/Nutch" , "JHotDraw");
    }
}
