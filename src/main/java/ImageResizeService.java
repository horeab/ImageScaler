import org.imgscalr.Scalr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageResizeService {


    private static boolean isPortraitOrientation = true;
    private static final Map<Integer, Color> IMG_NR_PAD_COLOR_MAP;

    static {
        Map<Integer, Color> aMap = new HashMap<>();
        //////////////MAIN//////////////////

        //ANATOMY
//        aMap.put(0, new Color(160, 221, 248));

        //ASTRONOMY
//        aMap.put(0, new Color(10, 13, 22));

        //DOPEWARS
//        aMap.put(0, new Color(199, 235, 199));

        //FAMPAINT
        aMap.put(0, new Color(222, 202, 159));

        //GEOQUIZ
//        aMap.put(0, new Color(196, 234, 252));

        //HANGMAN
//        aMap.put(0, new Color(74, 187, 34));

        //HISTORY
//        aMap.put(0, new Color(251, 234, 187));

        //IQTEST
//        aMap.put(0, new Color(255, 255, 255));
//        aMap.put(1, new Color(253, 194, 66));
//        aMap.put(2, new Color(199, 236, 254));
//        aMap.put(3, new Color(255, 255, 255));
//        aMap.put(4, new Color(255, 255, 255));
//        aMap.put(5, new Color(255, 255, 255));
//        aMap.put(6, new Color(255, 255, 255));
//        aMap.put(7, new Color(255, 255, 255));

        //JUDETELEROM
//        aMap.put(0, new Color(196, 234, 252));

        //PERSTEST
//        aMap.put(0, new Color(196, 234, 252));
        ////////////////////////////////////

        //WHITE
//        aMap.put(1, new Color(255, 255, 255));
        IMG_NR_PAD_COLOR_MAP = Collections.unmodifiableMap(aMap);
    }

    private static final int XS_WIDTH = 1242;
    private static final int XS_HEIGHT = 2688;

    private static final int _67_WIDTH = 1290;
    private static final int _67_HEIGHT = 2796;

    private static final int _8_WIDTH = 1242;
    private static final int _8_HEIGHT = 2208;

    private static final int IPAD_WIDTH = 2048;
    private static final int IPAD_HEIGHT = 2732;

    //ON RUN set "Working Directory" : WINDOWS !!!!!!!!!! C:\workspace\ImageResizer\src\main !!!!!!!!!!
    //ON RUN set "Working Directory" : MAC     !!!!!!!!!! /Users/macbook/IdeaProjects/ImageScaler/src/main !!!!!!!!!!
    public static void main(String[] args) {
//        List<Language> langs = Arrays.asList(Language.ro);
        List<Language> langs = Arrays.asList(Language.values());
        for (Language lang : langs) {
            for (int i = 2; i < 3; i++) {
//            for (int i = 0; i < 10; i++) {
                String imgName = lang.name() + i + ".png";

                resizeXS(imgName);
                resize67(imgName);
                BufferedImage resized8 = resize8(imgName);
                resizeIPad(resized8, imgName);
            }
        }
//        }
    }

    private static void resizeIPad(BufferedImage image, String imgName) {
        if (image == null) {
            return;
        }
        int padAmount = 220;
        image = Scalr.pad(image, padAmount, getPadColor(imgName));
        image = Scalr.crop(image, 0, padAmount, image.getWidth(), image.getHeight() - padAmount * 2);
        image = Scalr.resize(image, Scalr.Mode.FIT_EXACT, IPAD_WIDTH, IPAD_HEIGHT);
        saveImg(image, imgName, "scr_ipad", "png");
        System.out.println("IPad: Resized " + imgName);
    }

    private static BufferedImage resize8(String imgName) {
        BufferedImage image = getOriginalImage(imgName);
        if (image == null) {
            return null;
        }
        image = Scalr.resize(image, Scalr.Mode.FIT_EXACT, _8_WIDTH, _8_HEIGHT);
        saveImg(image, imgName, "scr_8", "png");
        System.out.println("8: Resized " + imgName);
        return image;
    }

    private static BufferedImage resizeXS(String imgName) {
        return resizeWithXSLine(imgName, "scr_xs", XS_HEIGHT, XS_WIDTH);
    }

    private static BufferedImage resize67(String imgName) {
        return resizeWithXSLine(imgName, "scr_67", _67_HEIGHT, _67_WIDTH);
    }

    private static BufferedImage resizeWithXSLine(String imgName, String scrFolder, int height, int width) {
        BufferedImage image = getOriginalImage(imgName);
        if (image == null) {
            return null;
        }
        int padAmount = (int) Math.round((image.getWidth() * (height / Double.valueOf(width)) - image.getHeight())) / 2;
        image = Scalr.pad(image, padAmount, getPadColor(imgName));
        image = Scalr.crop(image, padAmount, 0, image.getWidth() - padAmount * 2, image.getHeight());

        BufferedImage finalImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        BufferedImage xsBlackLine = new ImageLoadSaveService().load("xsblackline.png");
        g.drawImage(xsBlackLine, image.getWidth() / 2 - xsBlackLine.getWidth() / 2, image.getHeight() - 40, null);
        g.dispose();

        finalImage = Scalr.resize(finalImage, Scalr.Mode.FIT_EXACT, width, height);

        saveImg(finalImage, imgName, scrFolder, "png");
        System.out.println(scrFolder + ": Resized " + imgName);
        return finalImage;
    }

    private static BufferedImage getOriginalImage(String imgName) {
        BufferedImage img = new ImageLoadSaveService().load("scr_standard/" + imgName);
        if (!isPortraitOrientation && img != null) {
            img = Scalr.rotate(img, Scalr.Rotation.CW_90);
        }
        if (img != null) {
            if (img.getHeight() / Double.valueOf(img.getWidth()) > 1.77) {
                int padAmount = (int) Math.round(img.getHeight() - (img.getWidth() * 1.77));
                img = Scalr.pad(img, padAmount / 2, getPadColor(imgName));
            }
        }
        return img;
    }

    private static Color getPadColor(String imgName) {
        if (IMG_NR_PAD_COLOR_MAP.size() == 1) {
            return IMG_NR_PAD_COLOR_MAP.values().iterator().next();
        } else {
            String name = imgName.substring(0, imgName.indexOf("."));
            return IMG_NR_PAD_COLOR_MAP.get(Integer.valueOf(name.substring(name.length() - 1)));
        }
    }

    static void saveImg(BufferedImage image, String imgName, String scrFolder, String ext) {
        if (!isPortraitOrientation && image != null) {
            image = Scalr.rotate(image, Scalr.Rotation.CW_270);
        }
        String path = "resources/" + scrFolder;
        createDir(path);
        new ImageLoadSaveService().save(image, path + "/" + imgName, ext);
    }

    private static void createDir(String path) {
        java.io.File file = new java.io.File(path);
        file.mkdir();
    }
}
