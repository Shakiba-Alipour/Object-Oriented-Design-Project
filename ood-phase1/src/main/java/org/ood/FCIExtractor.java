package org.ood;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FCIExtractor extends VoidVisitorAdapter<Void> {
    private List<String> extendsList;
    private List<String> implementsList;
    private ArrayList<String> associatedList;
    private ArrayList<String> aggregatedList;
    private ArrayList<String> delegatedList;

    private ArrayList<String> compositedList;

    public static List<String> getFieldNames(ClassOrInterfaceDeclaration cls) {
        return cls.getFields().stream()
                .map(field -> field.getVariable(0).getNameAsString() + ": " + field.getElementType())
                .collect(Collectors.toList());
    }

    public static List<String> findChildClasses(CompilationUnit cu) {
        return cu.findAll(ClassOrInterfaceDeclaration.class)
                .stream()
                .filter(ClassOrInterfaceDeclaration::isNestedType)
                .map(ClassOrInterfaceDeclaration::getNameAsString)
                .collect(Collectors.toList());
    }

    public void findExtendsAndImplements(CompilationUnit cu, String className) {
        ClassOrInterfaceDeclaration cid = cu.getClassByName(className).orElse(null);
        if (cid == null) return;

        extendsList = cid.getExtendedTypes().stream()
                .filter(ClassOrInterfaceType.class::isInstance)
                .map(t -> t.asString())
                .collect(Collectors.toList());

        implementsList = cid.getImplementedTypes().stream()
                .filter(ClassOrInterfaceType.class::isInstance)
                .map(t -> t.asString())
                .collect(Collectors.toList());
    }

    public List<String> getExtendsList() {
        return extendsList != null ? extendsList : List.of();
    }

    public List<String> getImplementsList() {
        return implementsList != null ? implementsList : List.of();
    }

    public List<String> getAssociationList(ClassOrInterfaceDeclaration cls) {
        associatedList = new ArrayList<>();

        // Iterate over the implemented interfaces
        for (ClassOrInterfaceType implementedInterface : cls.getImplementedTypes()) {
            associatedList.add(String.valueOf(implementedInterface.getName()));
        }
        return associatedList;
    }

    public List<String> getAggregationList(FieldDeclaration fd) {
        aggregatedList = new ArrayList<>();
        if (fd.isPrivate() && !fd.isStatic()) {
            for (VariableDeclarator variableDeclarator : fd.getVariables()) {
//                ClassOrInterfaceType fieldType = (ClassOrInterfaceType) variableDeclarator.getType();
                String fieldType = variableDeclarator.getTypeAsString();
                aggregatedList.add(String.valueOf(fieldType));
            }
        }
        return aggregatedList;
    }

    public List<String> getDelegatedList(MethodDeclaration md) {
        delegatedList = new ArrayList<>();
//        if (md.getBody().isPresent()) {
//            md.getBody().get().findAll(MethodCallExpr.class).forEach(methodCall -> {
//                delegatedList.add(methodCall.resolve().getClassName());
//            });
//        }
        return delegatedList;
    }

    public List<String> getCompositedList(FieldDeclaration fd) {
        compositedList = new ArrayList<>();
        if (fd.isPrivate() && !fd.isStatic()) {
            for (VariableDeclarator variableDeclarator : fd.getVariables()) {
//                ClassOrInterfaceType fieldType = (ClassOrInterfaceType) variableDeclarator.getType();
                String fieldType = variableDeclarator.getTypeAsString();
                compositedList.add(String.valueOf(fieldType));
            }
        }
        return compositedList;
    }
}
