package org.ood;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectExtractor {
    static Parser parser;

    public static void extractProject(String projectPath, String projectName) throws IOException {

        parser = new Parser();
        File project = new File(projectPath);

        if (project.isDirectory()) {
            List<File> javaFiles = getJavaFiles(project);
            List<JavaClass> javaClassList = parser.parse(javaFiles);

            FileWriter writer = new FileWriter(projectName.concat(".csv"));
            writer.append("Project Name,Package Name,Class Name,Class Type,Class Visibility,Is Abstract," +
                    "Is Static,Is Final,Is Interface,Extends,Implements,Children,Fields,Methods,Overrides," +
                    "Static Methods,final Methods , Abstract Methods, Association, Aggregation, Delegation, " +
                    "Composition, Instantiation, API\n");

            for (JavaClass jclass : javaClassList) {
                writer.append(projectName).append(",");
                writer.append(jclass.packageName).append(",");
                writer.append(jclass.name).append(",");
                writer.append(String.valueOf(jclass.type)).append(",");
                writer.append(String.valueOf(jclass.visibility)).append(",");
                writer.append(String.valueOf(jclass.isAbstract)).append(",");
                writer.append(String.valueOf(jclass.isStatic)).append(",");
                writer.append(String.valueOf(jclass.isFinal)).append(",");
                writer.append(String.valueOf(jclass.isInterface)).append(",");

                for (String extendedClass : jclass.Extends) {
                    writer.append(extendedClass).append(" ");
                }
                writer.append(",");

                for (String implementedInterface : jclass.Implements) {
                    writer.append(implementedInterface).append(" ");
                }
                writer.append(",");

                for (String child : jclass.children) {
                    writer.append(child).append(" ");
                }
                writer.append(",");

                for (String field : jclass.fields) {
                    writer.append(field).append(" ");
                }
                writer.append(",");

                for (MethodData method : jclass.methods) {
                    writer.append(method.getSignature()).append("} ");
                }
                writer.append(",");

                for (MethodData omethod : jclass.overrideMethods) {
                    writer.append(omethod.getName()).append(" ");
                }
                writer.append(",");

                for (String smthod : jclass.staticMethods) {
                    writer.append(smthod).append(" ");
                }
                writer.append(",");

                for (String fmethod : jclass.finalMethods) {
                    writer.append(fmethod).append(" ");
                }
                writer.append(",");

                for (String amethod : jclass.abstractMethods) {
                    writer.append(amethod).append(" ");
                }
                writer.append(",");

                for (String associated : jclass.associatedList) {
                    writer.append(associated).append(" ");
                }
                writer.append(",");

                for (String aggregated : jclass.aggregatedList) {
                    writer.append(aggregated).append(" ");
                }
                writer.append(",");

                for (String delegated : jclass.delegatedList) {
                    writer.append(delegated).append(" ");
                }
                writer.append(",");

                for (String composited : jclass.compositedList) {
                    writer.append(composited).append(" ");
                }
                writer.append(",");

                for (String instantiated : jclass.instantiatedList) {
                    writer.append(instantiated).append(" ");
                }
                writer.append(",");

                writer.append(jclass.api).append("\n");
            }

            writer.close();
            System.out.println("The extraction is done.");
        } else {
            System.out.println("The project path is not a valid directory!");
        }
    }

    public static List<File> getJavaFiles(File directory) {
        List<File> javaFiles = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                javaFiles.addAll(getJavaFiles(file));
            } else {
                if (file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }
        return javaFiles;
    }
}
