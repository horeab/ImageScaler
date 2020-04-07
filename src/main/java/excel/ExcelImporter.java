package excel;

import excel.model.ChemicalElement;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelImporter {

    public static void main(String[] args) throws IOException {
        process();
    }

    //ON RUN: !!!!!!!!!! C:\workspace\ImageResizer\src\main !!!!!!!!!!
    private static void process() throws IOException {
        FileInputStream inputStream = new FileInputStream(
                ExcelImporter.class.getResource("../../resources/excel/Book1.xlsx").getFile());
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        int rowIndex = 0;
        List<ChemicalElement> elements = new ArrayList<>();
        String objectName = null;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            //The first row is the Title Row. We Ignore it
            int cellIndex = 0;
            Iterator<Cell> cellIterator = row.cellIterator();
            ChemicalElement chemicalElement = new ChemicalElement();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cellIndex == 0) {
                    chemicalElement.setAtomicNumber((int) cell.getNumericCellValue());
                }
                if (cellIndex == 1) {
                    chemicalElement.setSymbol(cell.getStringCellValue());
                }
                if (cellIndex == 2) {
                    chemicalElement.setName(cell.getStringCellValue());
                }
                if (cellIndex == 6) {
                    chemicalElement.setAtomicWeight(processNumber(getVal(cell)));
                }
                if (cellIndex == 7) {
                    chemicalElement.setDensity(processNumber(getVal(cell)));
                }
                if (cellIndex == 8) {
                    chemicalElement.setMeltingPoint(processNumber(getVal(cell)));
                }
                if (cellIndex == 9) {
                    chemicalElement.setBoilingPoint(processNumber(getVal(cell)));
                }
                cellIndex++;
            }
            System.out.println(chemicalElement);
        }
    }

    private static String getVal(Cell cell) {
        try {
            return String.valueOf(cell.getNumericCellValue());
        } catch (Exception e) {
            return cell.getStringCellValue();
        }
    }

    private static String processNumber(String val) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < val.toCharArray().length; i++) {
            String str = String.valueOf(val.toCharArray()[i]);
            if (NumberUtils.isNumber(str) || str.equals(".") || str.equals("~") || str.equals("-") || str.equals("E")) {
                res.append(str);
            }
        }
        return res.toString();
    }
}
