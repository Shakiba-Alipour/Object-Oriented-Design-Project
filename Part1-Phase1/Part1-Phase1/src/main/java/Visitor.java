import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.List;

public class Visitor extends VoidVisitorAdapter<Void> {
    private XSSFSheet sheet;
    Cell cell;

    Visitor(XSSFSheet sheet, String projectName) {
        this.sheet = sheet;

        fillColumnsNames();

        // row 0 : project name
        Cell cell = this.sheet.getRow(0).createCell(1);
        cell.setCellValue(projectName);

    }

    XSSFSheet getSheet() {
        return this.sheet;
    }

    private void fillColumnsNames() {

        // row 0
        Cell cell = this.sheet.createRow(0).createCell(0);
        cell.setCellValue("Project Name");

        // row 1
        cell = this.sheet.createRow(1).createCell(0);
        cell.setCellValue("Package_Name");

        // row 2
        cell = this.sheet.createRow(2).createCell(0);
        cell.setCellValue("Class_Name");

        // row 3
        cell = this.sheet.createRow(3).createCell(0);
        cell.setCellValue("Class_Type");

        // row 4
        cell = this.sheet.createRow(4).createCell(0);
        cell.setCellValue("Class_Visibility");

        // row 5
        cell = this.sheet.createRow(5).createCell(0);
        cell.setCellValue("Class_is_Abstract");

        // row 6
        cell = this.sheet.createRow(6).createCell(0);
        cell.setCellValue("Class_is_Static");

        // row 7
        cell = this.sheet.createRow(7).createCell(0);
        cell.setCellValue("Class_is_Final");

        // row 8
        cell = this.sheet.createRow(8).createCell(0);
        cell.setCellValue("Class_is_Interface");

        // row 9
        cell = this.sheet.createRow(9).createCell(0);
        cell.setCellValue("extends");

        // row 10
        cell = this.sheet.createRow(10).createCell(0);
        cell.setCellValue("implements");

        // row 11
        cell = this.sheet.createRow(11).createCell(0);
        cell.setCellValue("children");

        // row 12
        cell = this.sheet.createRow(12).createCell(0);
        cell.setCellValue("constructor");

        // row 13
        cell = this.sheet.createRow(13).createCell(0);
        cell.setCellValue("Fields");

        // row 14
        cell = this.sheet.createRow(14).createCell(0);
        cell.setCellValue("Methods");

        // row 15
        cell = this.sheet.createRow(15).createCell(0);
        cell.setCellValue("override");

        // row 16
        cell = this.sheet.createRow(16).createCell(0);
        cell.setCellValue("has_static_method");

        // row 17
        cell = this.sheet.createRow(17).createCell(0);
        cell.setCellValue("has_final_method");

        // row 18
        cell = this.sheet.createRow(18).createCell(0);
        cell.setCellValue("has_abstract_method");

        // row 19
        cell = this.sheet.createRow(19).createCell(0);
        cell.setCellValue("Association");

        // row 20
        cell = this.sheet.createRow(20).createCell(0);
        cell.setCellValue("Aggregation");

        // row 21
        cell = this.sheet.createRow(21).createCell(0);
        cell.setCellValue("Delegation");

        // row 22
        cell = this.sheet.createRow(22).createCell(0);
        cell.setCellValue("Composition");

        // row 23
        cell = this.sheet.createRow(23).createCell(0);
        cell.setCellValue("Instantiation");

        // row 24
        cell = this.sheet.createRow(24).createCell(0);
        cell.setCellValue("API");

        // row 25
        cell = this.sheet.createRow(25).createCell(0);
        cell.setCellValue("pattern");

        // row 26
        cell = this.sheet.createRow(26).createCell(0);
        cell.setCellValue("role");

        // row 27
        cell = this.sheet.createRow(27).createCell(0);
        cell.setCellValue("description");

    }

    @Override
    public void visit(PackageDeclaration pd, Void arg) {
        super.visit(pd, arg);
        String packageName = pd.getName().toString();
        // row 1: package name
        cell = this.sheet.getRow(1).createCell(1);
        cell.setCellValue(packageName);
    }

    @Override
    public void visit(FieldDeclaration fd, Void arg) {
        super.visit(fd, arg);

        // row 13: fields
        cell = this.sheet.getRow(13).createCell(1);
        if (!cell.getStringCellValue().isEmpty()) {
            cell.setCellValue(cell.getStringCellValue() + ", " + fd.getVariables().get(0).getNameAsString() +
                    " of type " + fd.getCommonType());
        } else {
            cell.setCellValue(fd.getVariables().get(0).getNameAsString() +
                    " of type " + fd.getCommonType());
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration cid, Void arg) {

        super.visit(cid, arg);

        // row 2: class name
        cell = this.sheet.getRow(2).createCell(1);
        cell.setCellValue(cid.getNameAsString());

        // row 3: class type
        // row 8: check if it's an interface
        if (!cid.isInterface() && !cid.isNestedType()) {
            cell = this.sheet.getRow(3).createCell(1);
            cell.setCellValue("1");
        } else if (cid.isInterface()) {
            cell = this.sheet.getRow(3).createCell(1);
            cell.setCellValue("2");
            cell = this.sheet.getRow(8).createCell(1);
            cell.setCellValue("true");
        } else if (cid.isNestedType()) {
            cell = this.sheet.getRow(3).createCell(1);
            cell.setCellValue("3");
        }

        // row 4: class visibility
        if (cid.isPublic()) {
            cell = this.sheet.getRow(4).createCell(1);
            cell.setCellValue("1");
        } else if (cid.isPrivate()) {
            cell = this.sheet.getRow(4).createCell(1);
            cell.setCellValue("2");
        } else if (cid.isProtected()) {
            cell = this.sheet.getRow(4).createCell(1);
            cell.setCellValue("3");
        }

        // row 5 : class_is_abstract
        if (cid.isAbstract()) {
            cell = this.sheet.getRow(5).createCell(1);
            cell.setCellValue("1");
        } else {
            cell = this.sheet.getRow(5).createCell(1);
            cell.setCellValue("0");
        }

        // row 6 : class_is_static
        if (cid.isStatic()) {
            cell = this.sheet.getRow(6).createCell(1);
            cell.setCellValue("1");
        } else {
            cell = this.sheet.getRow(6).createCell(1);
            cell.setCellValue("0");
        }

        // row 7: check if the class is final
        if (cid.isFinal()) {
            cell = this.sheet.getRow(7).createCell(1);
            cell.setCellValue("true");
        } else {
            cell = this.sheet.getRow(7).createCell(1);
            cell.setCellValue("0");
        }

        // row 9: extends
        if (cid.getExtendedTypes().size() > 0) {
            cell = this.sheet.getRow(9).createCell(1);
            for (ClassOrInterfaceType extendedType : cid.getExtendedTypes()) {
                cell.setCellValue(cell.getStringCellValue() + ", " + extendedType.asString());
            }
        } else {
            cell.setCellValue("0");
        }

        // row 10: implement=ts
        if (cid.getImplementedTypes().size() > 0) {
            cell = this.sheet.getRow(10).createCell(1);
            for (ClassOrInterfaceType implementedType : cid.getImplementedTypes()) {
                cell.setCellValue(cell.getStringCellValue() + ", " + implementedType.asString());
            }
        } else {
            cell.setCellValue("0");
        }

        // row 11: children
        List<ClassOrInterfaceDeclaration> children = cid.findAll(ClassOrInterfaceDeclaration.class);
        cell = this.sheet.getRow(11).createCell(1);
        if (children.isEmpty()) {
            cell.setCellValue("0");
        } else if (children.size() == 1) {
            cell.setCellValue(children.get(0).getNameAsString());
        } else {
            for (int i = 0; i < children.size(); i++) {
                cell.setCellValue(children.get(i).getNameAsString());
                cell.setCellValue(cell.getStringCellValue() + ", " + children.get(i).getNameAsString());
            }
        }

        // row 12: constructors
        List<ConstructorDeclaration> constructors = cid.findAll(ConstructorDeclaration.class);
        cell = this.sheet.getRow(12).createCell(1);
        if (constructors.isEmpty()) {
            cell.setCellValue("no constructors");
        } else if (constructors.size() == 1) {
            cell.setCellValue(constructors.get(0).getAccessSpecifier().toString() + " " +
                    constructors.get(0).getNameAsString());
        } else {
            for (int i = 0; i < constructors.size(); i++) {
                cell.setCellValue(constructors.get(i).getNameAsString());
                cell.setCellValue(cell.getStringCellValue() + ", " +
                        constructors.get(0).getAccessSpecifier().toString() + " " +
                        constructors.get(0).getNameAsString());
            }
        }
    }

    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);

        // row 14: methods
        String parameters = null;
        for (int i = 0; i < md.getParameters().size(); i++) {
            if (i > 0) {
                parameters += ", ";
            }
            parameters += md.getParameters().get(i).getTypeAsString();
        }

        cell.setCellValue(cell.getRichStringCellValue() + "\n" + md.getTypeAsString()
                + " " + md.getNameAsString() + " (" + parameters + ")");
        cell = this.sheet.getRow(14).createCell(1);
        String str = md.getType() + " " + md.getNameAsString() + "(";
        for (Parameter parameter :
                md.getParameters()) {
            str += parameter.getType() + " " + parameter.getName();
        }
        str += ")";
        if (!cell.getStringCellValue().isEmpty()) {
            cell.setCellValue(cell.getStringCellValue() + "\n" + str);
        } else {
            cell.setCellValue(str);
        }
    }

}