import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Parser {

    private JavaParser jp;
    private final File file;
    private final ParseResult<CompilationUnit> cu;
    private String projectName;
    private BufferedReader fileReader;
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public Parser(File file, String projectName, XSSFWorkbook workbook) throws FileNotFoundException {
        this.file = file;
        this.projectName = projectName;
        jp = new JavaParser();
        this.fileReader = new BufferedReader(new FileReader(file));
        this.cu = jp.parse(file);
        this.workbook = workbook;
        //create sheet
//        String[] completeFileName = file.getName().split(".");
        this.sheet = workbook.createSheet(projectName + "-" + file.getName());
    }


    private void parse() throws IOException {

        this.fileReader = new BufferedReader(new FileReader(file));

        ParseResult<CompilationUnit> parseResult = jp.parse(this.file);
        CompilationUnit cu = null;
        if (parseResult.isSuccessful()) {
            cu = parseResult.getResult().get();
        } else {
            System.out.println(parseResult.getProblems());
        }

        Visitor visitor = new Visitor(this.sheet, projectName);

        // package declaration
        visitor.visit(cu.getPackageDeclaration().get(), null);

        // class declaration
        for (ClassOrInterfaceDeclaration cid :
                cu.findAll(ClassOrInterfaceDeclaration.class)) {
            visitor.visit(cid, null);
        }

        // field declaration
        for (FieldDeclaration fd :
                cu.findAll(FieldDeclaration.class)) {
            visitor.visit(fd, null);
        }

        // method declaration
        for (MethodDeclaration md :
                cu.findAll(MethodDeclaration.class)) {
            visitor.visit(md, null);
        }

        this.sheet = visitor.getSheet();
    }

    public static void main(String[] args) throws IOException {
//        DirectoryExplorer ex=new DirectoryExplorer("QuickUML","D:\\University\Object Oriented Design\\Project\Part1-Phase1\\Open Source OO project-20230329\\1 - QuickUML 2001");
        XSSFWorkbook workbook = new XSSFWorkbook();
        File file = new File("GifEncoder.java");
        Parser parser = new Parser(file, "project", workbook);
        parser.parse();
    }
}
