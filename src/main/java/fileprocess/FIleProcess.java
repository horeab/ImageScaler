package fileprocess;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FIleProcess {

    //ON RUN: !!!!!!!!!! C:\workspace\ImageResizer\src\main !!!!!!!!!!
    public static void main(String[] args) throws IOException {
        pro();
    }


    private static void pro() throws IOException {
        String fileName = "toProcess";
        File file = new File("resources/file/countries_q/" + fileName + ".txt");
        Scanner sc = new Scanner(file);

        List<String> index = new ArrayList<>();
        List<String> name = new ArrayList<>();
        int j = 1;
        while (sc.hasNextLine()) {
            int i = 1;
            String fileCountry = sc.nextLine();
            File cFile = new File("resources/file/countries_q/c_en.txt");
            Scanner csc = new Scanner(cFile);
            while (csc.hasNextLine()) {
                String country = csc.nextLine();
                if (fileCountry.trim().startsWith(country.trim())) {
                    index.add(i + "");
                    name.add(j + " " + country);
                    break;
                }
                i++;
            }
            j++;
        }
        System.out.println(String.join(",", index));
        System.out.println(String.join(",", name));
        System.out.println(index.size() + "");
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
