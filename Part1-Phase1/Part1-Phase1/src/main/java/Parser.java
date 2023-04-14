import com.github.javaparser.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Parser {
    private JavaParser jp;
    private String projectName;
    private BufferedReader file;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public Parser(File file, String projectName, XSSFWorkbook workbook) throws FileNotFoundException {
        this.projectName = projectName;
        this.jp = new JavaParser();
        this.file = new BufferedReader(new FileReader(file));
        jp.parse(file);
        this.workbook = workbook;
        //create sheet
        String[] completeFileName = file.getName().split(".");
        this.sheet = workbook.createSheet(projectName + "/" + completeFileName[0]);
        fillColumnsNames();
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

    private void parse() throws IOException {

        Cell cell = this.sheet.createRow(0).createCell(1);
        cell.setCellValue(projectName);
        while (true) {
            String line = this.file.readLine();

        }
    }

}
