import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageResizeService {

    private static final Color PAD_COLOR = new Color(90, 188, 148);

    private static final int XS_WIDTH = 1242;
    private static final int XS_HEIGHT = 2688;

    private static final int _8_WIDTH = 1242;
    private static final int _8_HEIGHT = 2208;

    private static final int IPAD_WIDTH = 2048;
    private static final int IPAD_HEIGHT = 2732;

    private static final String ROOT_DIR = "src\\main\\resources\\";

    public static void main(String[] args) {
        for (Language lang : Language.values()) {
            for (int i = 0; i < 5; i++) {
                String imgName = lang.name() + i + ".PNG";
                resizeIPad(resize8(resizeXS(imgName), imgName), imgName);
                System.out.println(imgName);
            }
        }
    }

    private static void resizeIPad(BufferedImage image, String imgName) {
        if (image == null) {
            return;
        }
        int padAmount = 220;
        image = Scalr.pad(image, padAmount, PAD_COLOR);
        image = Scalr.crop(image, 0, padAmount, image.getWidth(), image.getHeight() - padAmount * 2);
        image = Scalr.resize(image, Scalr.Mode.FIT_EXACT, IPAD_WIDTH, IPAD_HEIGHT);
        String path = ROOT_DIR + "scr_ipad";
        createDir(path);
        ImageLoadSaveService.save(image, path + "\\" + imgName);
    }

    private static BufferedImage resize8(BufferedImage image, String imgName) {
        if (image == null) {
            return null;
        }
        image = Scalr.crop(image, 0, (XS_HEIGHT - _8_HEIGHT) / 2, _8_WIDTH, _8_HEIGHT);
        String path = ROOT_DIR + "scr_8";
        createDir(path);
        ImageLoadSaveService.save(image, path + "\\" + imgName);
        return image;
    }

    private static BufferedImage resizeXS(String imgName) {
        BufferedImage image = ImageLoadSaveService.load(ROOT_DIR + "scr_standard\\" + imgName);
        if (image == null) {
            return null;
        }
        BufferedImage xsBlackLine = ImageLoadSaveService.load("xsblackline.png");
        int padAmount = 92;
        image = Scalr.pad(image, padAmount, PAD_COLOR);
        image = Scalr.crop(image, padAmount, 0, image.getWidth() - padAmount * 2, image.getHeight());
        image = Scalr.resize(image, Scalr.Mode.FIT_EXACT, XS_WIDTH, XS_HEIGHT);

        final BufferedImage finalImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(xsBlackLine, image.getWidth() / 2 - xsBlackLine.getWidth() / 2, image.getHeight() - 40, null);
        g.dispose();
        String path = ROOT_DIR + "scr_xs";
        createDir(path);
        ImageLoadSaveService.save(finalImage, path + "\\" + imgName);
        return finalImage;
    }

    private static void createDir(String path) {
        java.io.File file = new java.io.File(path);
        file.mkdir();
    }
}
