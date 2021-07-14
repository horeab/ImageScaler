import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class ImageExtraContentResizeService {

    private static final Color PAD_COLOR_MAIN = new Color(9, 10, 56);

    private static final int _8_WIDTH = 1242;
    private static final int _8_HEIGHT = 2208;

    public static void main(String[] args) {
        String imgName = "capture.PNG";
        resize(imgName);
    }

    //ON RUN: !!!!!!!!!! C:\workspace\ImageResizer\src\main !!!!!!!!!!
    private static void resize(String imgName) {
        BufferedImage image = new ImageLoadSaveService().load("../resources/extra_content/" + imgName);
        image = Scalr.resize(image, Scalr.Mode.FIT_EXACT, _8_WIDTH, _8_HEIGHT);
        ImageResizeService.saveImg(image, imgName, "extra_content");
        System.out.println("Resized " + imgName);
    }
}
