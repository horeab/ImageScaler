package fileprocess;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import fileprocess.model.CountryNeighbours;

public class FIleProcess {

    //ON RUN: !!!!!!!!!! C:\workspace\ImageResizer\src\main !!!!!!!!!!
    public static void main(String[] args) throws IOException {
//        process();
        countries();
    }


    private static void countries() throws IOException {
        XSSFWorkbook basicInfo = new XSSFWorkbook("resources/file/countries_q/cc.xlsx");
        XSSFSheet yearSheet = basicInfo.getSheetAt(0);
        Iterator<Row> rowIterator = yearSheet.rowIterator();
        List<CountryNeighbours> elements = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int cellIndex = 0;
            Iterator<Cell> cellIterator = row.cellIterator();
            CountryNeighbours el = new CountryNeighbours();
            try {
                while (cellIterator.hasNext()) {
                    XSSFCell cell = (XSSFCell) cellIterator.next();
                    if (cellIndex == 1) {
                        el.countryName = cell.getStringCellValue().trim();
                    }
                    if (cellIndex == 2) {
                        el.val = cell.getStringCellValue().split("\\(")[0].trim().replace(",", "");
                    }
                    cellIndex++;
                }
            } catch (Exception e) {
                int i = 0;
            }

            elements.add(el);
        }

        File cFile = new File("resources/file/countries_q/c_en.txt");
        Scanner csc = new Scanner(cFile);
        List<String> allC = new ArrayList<>();
        while (csc.hasNextLine()) {
            allC.add(csc.nextLine());
        }
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        List<String> vals = new ArrayList<>();
        List<Integer> vali = new ArrayList<>();
        elements.forEach(el -> {
            int j = 1;
            for (String country : allC) {
                if (el.countryName.trim().startsWith(country.trim())) {
                    String cQ = j + ":" + el.val;
                    vals.add(cQ);
                    if (vali.contains(j)) {
                        System.out.println(j + "");
                    }
                    vali.add(j);
//                    System.out.println(country + ":" + df.format(el.val));
                }
                j++;
            }
        });
        for (int i = 1; i <= 195; i++) {
            if (!vali.contains(i)) {
                System.out.println(i + "xx");
            }
        }
        vals.stream().forEach(v -> System.out.println(v));
    }

    private static void pro() throws IOException {
        String fileName = "nei";
        File file = new File("resources/file/countries_q/" + fileName + ".txt");
        Scanner sc = new Scanner(file);

        List<String> neighb = new ArrayList<>();
        List<String> neighbS = new ArrayList<>();
        while (sc.hasNextLine()) {
            int i = 1;
            String neighbour = sc.nextLine();
            File cFile = new File("resources/file/countries_q/c_en.txt");
            Scanner csc = new Scanner(cFile);
            while (csc.hasNextLine()) {
                String country = csc.nextLine();
                if (neighbour.trim().startsWith(country.trim())) {
                    neighb.add(i + "");
                    neighbS.add(country);
                    break;
                }
                i++;
            }
        }
        System.out.println(String.join(",", neighb));
        System.out.println(String.join(",", neighbS));
        System.out.println(neighb.size() + "");
    }

    private static void process() throws IOException {
//        File file = new File("resources/file/countries_q/file.txt");
//        Scanner sc = new Scanner(file);
//
//        List<String> invalidChars = Arrays.asList("[", "\"");
//
//        System.out.println("////////////////////////");
//        System.out.println("////////////////////////");
//        System.out.println("////////////////////////");
//        BufferedWriter writer = new BufferedWriter(new FileWriter("resources/file/countries_q/c_de.txt"));
//        while (sc.hasNextLine()) {
//            String countryName = sc.nextLine();
//            for (String c : invalidChars) {
//                int index = countryName.indexOf(c);
//                if (index != -1) {
//                    countryName = countryName.substring(0, index);
//                }
//            }
//            countryName = removeBrackets(countryName);
//            if (StringUtils.isNotBlank(countryName)
//                    && NumberUtils.isNumber(String.valueOf(countryName.charAt(0)))) {
//                countryName = Arrays.stream(countryName.split("\t")).map(c ->
//                        NumberUtils.isNumber(c) ? "" : c).collect(Collectors.joining(" "));
//                System.out.println(countryName.trim());
//                writer.write(countryName.trim() + "\n");
//            }
//        }
//        writer.close();
//        System.out.println("////////////////////////");
//        System.out.println("////////////////////////");
//        System.out.println("////////////////////////");
    }

    private static String removeBrackets(String string) {
        return Arrays.stream(string.split(" ")).map(s -> s.contains("(") || StringUtils.isBlank(s) ? "" : s.trim()).collect(Collectors.joining(" ")).replaceAll(" +", " ");
    }
}
