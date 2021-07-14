package fileprocess;

import com.google.gson.Gson;
import excel.model.ChemicalElement;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class ExcelImporter {

    //ON RUN: !!!!!!!!!! C:\workspace\ImageResizer\src\main !!!!!!!!!!
    public static void main(String[] args) throws IOException {
        process();
//        String lang = "zh";
//        translate(lang);
    }

    private static void translate(String lang) throws IOException {
        XSSFWorkbook basicInfo = new XSSFWorkbook(new FileInputStream(
                ExcelImporter.class.getResource("../../resources/excel/translation.xlsx").getFile()));
        XSSFSheet sheet = basicInfo.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<ChemicalElement> elements = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int cellIndex = 0;
            Iterator<Cell> cellIterator = row.cellIterator();
            ChemicalElement chemicalElement = new ChemicalElement();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                try {
                    if (cellIndex == 0) {
                        chemicalElement.setAtomicNumber(((int) cell.getNumericCellValue()));
                    }
                    if (cellIndex == 2) {
                        String name = cell.getStringCellValue();
                        chemicalElement.setName(name.contains("(") ? name.substring(0, name.indexOf("(")) : name);
                        name = chemicalElement.getName();
                        chemicalElement.setName(name.contains(",") ? name.substring(0, name.indexOf(",")) : name);
                    }
                } catch (Exception e) {
                }
                cellIndex++;
            }
            if (chemicalElement.getAtomicNumber() != 0) {
                elements.add(chemicalElement);
            }
        }
        List<ChemicalElement> sortedElements = new ArrayList<>(elements);
        Collections.sort(sortedElements, new Comparator<ChemicalElement>() {
            @Override
            public int compare(ChemicalElement r1, ChemicalElement r2) {
                return Integer.compare(r1.getAtomicNumber(), r2.getAtomicNumber());
            }
        });
        for (ChemicalElement e : sortedElements) {
            System.out.println(lang + "_periodictable_" + e.getAtomicNumber() + "=" + StringUtils.capitalize(e.getName()).trim());
        }
    }

    private static void process() throws IOException {
        XSSFWorkbook basicInfo = new XSSFWorkbook(new FileInputStream(
                ExcelImporter.class.getResource("../../resources/excel/basicinfo.xlsx").getFile()));
        XSSFSheet sheet = basicInfo.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<ChemicalElement> elements = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
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
                if (cellIndex == 4) {
                    chemicalElement.setGroup(processNumber(getVal(cell)));
                }
                if (cellIndex == 5) {
                    chemicalElement.setPeriod(processNumber(getVal(cell)));
                }
                if (cellIndex == 6) {
                    chemicalElement.setAtomicWeight(processNumberToString(getVal(cell)));
                }
                if (cellIndex == 7) {
                    chemicalElement.setDensity(processNumberToString(getVal(cell)));
                }
                if (cellIndex == 8) {
                    chemicalElement.setMeltingPoint(processNumberToString(getVal(cell)));
                }
                if (cellIndex == 9) {
                    chemicalElement.setBoilingPoint(processNumberToString(getVal(cell)));
                }
                if (cellIndex == 13) {
                    chemicalElement.setType(processNumber(getVal(cell)));
                }
                cellIndex++;
            }
            elements.add(chemicalElement);
        }

        XSSFWorkbook yearDisc = new XSSFWorkbook(new FileInputStream(
                ExcelImporter.class.getResource("../../resources/excel/year.xlsx").getFile()));
        sheet = yearDisc.getSheetAt(0);

        for (int i = 1; i <= 118; i++) {
            ChemicalElement el = getElementByNr(i, elements);
            rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int cellIndex = 0;
                Iterator<Cell> cellIterator = row.cellIterator();
                int aNr = -1;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cellIndex == 0) {
                        aNr = (int) cell.getNumericCellValue();
                    }
                    if (aNr == i && cellIndex == 2) {
                        el.setYearOfDiscovery(processNumber(getVal(cell)));
                    }
                    if (aNr == i && cellIndex == 3) {
                        el.setDiscoveredBy(getVal(cell));
                    }
                    cellIndex++;
                }
            }
        }
        printElementsInfo(elements);
    }

    private static void printElementsInfo(List<ChemicalElement> elements) {
        for (ChemicalElement e : elements) {
            System.out.println(new Gson().toJson(e));
        }
        for (ChemicalElement e : elements) {
//            System.out.println("en_periodictable_" + e.getAtomicNumber() + "=" + e.getName());
        }
    }

    private static ChemicalElement getElementByNr(int nr, List<ChemicalElement> elements) {
        for (ChemicalElement e : elements) {
            if (e.getAtomicNumber() == nr) {
                return e;
            }
        }
        return null;
    }

    private static String getVal(Cell cell) {
        try {
            String s = String.valueOf(cell.getNumericCellValue());
            if (s.contains("E")) {
                NumberFormat formatter = new DecimalFormat("0.#####");
                s = formatter.format(cell.getNumericCellValue());
            }
            return s;
        } catch (Exception e) {
            return cell.getStringCellValue();
        }
    }

    private static int processNumber(String val) {
        double res = 0;
        if (val.contains("BC")) {
            res = -Integer.valueOf(val.replace("BC", "").replace(" ", "").trim());
        } else {
            res = Double.valueOf(val.replace("AD", "").replace(" ", "").trim());
        }
        return (int) res;
    }

    private static String processNumberToString(String val) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < val.toCharArray().length; i++) {
            String str = String.valueOf(val.toCharArray()[i]);
            if (NumberUtils.isNumber(str) || str.equals(".") || str.equals("~") || str.equals("-")) {
                res.append(str);
            }
        }
        return res.toString();
    }
}
