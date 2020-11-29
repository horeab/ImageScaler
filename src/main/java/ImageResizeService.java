import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageResizeService {

    private static final Color PAD_COLOR = new Color(190, 237, 252);

    private static final boolean isLandscape = false;
    private static final Map<Integer, Color> INDEX_PAD_COLOR = new HashMap<>();

    static {
//        INDEX_PAD_COLOR.put(3, new Color(192, 249, 185));
//        INDEX_PAD_COLOR.put(5, new Color(226, 241, 253));
    }


    private static final int XS_WIDTH = 1242;
    private static final int XS_HEIGHT = 2688;

    private static final int _8_WIDTH = 1242;
    private static final int _8_HEIGHT = 2208;

    private static final int IPAD_WIDTH = 2048;
    private static final int IPAD_HEIGHT = 2732;

    //ON RUN: !!!!!!!!!! C:\workspace\ImageResizer\src\main !!!!!!!!!!
    public static void main(String[] args) {
//        List<Language> langs = Arrays.asList(Language.de);
        List<Language> langs = Arrays.asList(Language.values());
        for (Language lang : langs) {
            for (int i = 0; i <= 5; i++) {
                ////////////////////STANDARD////////////////////
                String imgName = lang.name() + i + ".PNG";
                ////////////////////////////////////////////
                ////////////////////UNIQUE//////////////////
//                String imgName = "2" + lang.name() + ".PNG";
                ////////////////////////////////////////////////
                resizeIPad(resize8(resizeXS(imgName, i), imgName), imgName, i);
            }
        }
    }

    private static void resizeIPad(BufferedImage image, String imgName, int index) {
        if (image == null) {
            return;
        }
        if (isLandscape) {
            image = Scalr.rotate(image, Scalr.Rotation.CW_90);
        }
        int padAmount = 220;
        image = Scalr.pad(image, padAmount, INDEX_PAD_COLOR.getOrDefault(index, PAD_COLOR));
        image = Scalr.crop(image, 0, padAmount, image.getWidth(), image.getHeight() - padAmount * 2);
        image = Scalr.resize(image, Scalr.Mode.FIT_EXACT, IPAD_WIDTH, IPAD_HEIGHT);
        saveImg(image, imgName, "scr_ipad");
        System.out.println("IPad: Resized " + imgName);
    }

    private static BufferedImage resize8(BufferedImage image, String imgName) {
        if (image == null) {
            return null;
        }
        if (isLandscape) {
            image = Scalr.rotate(image, Scalr.Rotation.CW_90);
        }
        image = Scalr.crop(image, 0, (XS_HEIGHT - _8_HEIGHT) / 2, _8_WIDTH, _8_HEIGHT);
        saveImg(image, imgName, "scr_8");
        System.out.println("8: Resized " + imgName);
        if (isLandscape) {
            image = Scalr.rotate(image, Scalr.Rotation.CW_270);
        }
        return image;
    }

    private static BufferedImage resizeXS(String imgName, int index) {
        BufferedImage image = new ImageLoadSaveService().load("../resources/" + "scr_standard/" + imgName);
        if (image == null) {
            return null;
        }
        if (isLandscape) {
            image = Scalr.rotate(image, Scalr.Rotation.CW_90);
        }
        BufferedImage xsBlackLine = new ImageLoadSaveService().load("xsblackline.png");
        int padAmount = 92;
        image = Scalr.pad(image, padAmount, INDEX_PAD_COLOR.getOrDefault(index, PAD_COLOR));
        image = Scalr.crop(image, padAmount, 0, image.getWidth() - padAmount * 2, image.getHeight());
        image = Scalr.resize(image, Scalr.Mode.FIT_EXACT, XS_WIDTH, XS_HEIGHT);

        BufferedImage finalImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(xsBlackLine, image.getWidth() / 2 - xsBlackLine.getWidth() / 2, image.getHeight() - 40, null);
        g.dispose();
        saveImg(finalImage, imgName, "scr_xs");
        System.out.println("XS: Resized " + imgName);
        if (isLandscape) {
            finalImage = Scalr.rotate(finalImage, Scalr.Rotation.CW_270);
        }
        return finalImage;
    }

    static void saveImg(BufferedImage image, String imgName, String scrFolder) {
        String path = "resources/" + scrFolder;
        createDir(path);
        if (isLandscape) {
            image = Scalr.rotate(image, Scalr.Rotation.CW_270);
        }
        new ImageLoadSaveService().save(image, path + "/" + imgName);
    }

    private static void createDir(String path) {
        java.io.File file = new java.io.File(path);
        file.mkdir();
    }
}
