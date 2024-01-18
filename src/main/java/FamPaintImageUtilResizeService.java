import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

public class FamPaintImageUtilResizeService {

    private static final int _WIDTH = 150;
    private static final int _HEIGHT = 150;

    public static void main(String[] args) {
        for (int cat = 0; cat < 5; cat++) {
            for (int imgNr = 0; imgNr < 40; imgNr++) {
                resize(imgNr, cat);
            }
        }
    }

    //ON RUN set "Working Directory" : WINDOWS !!!!!!!!!! C:\workspace\ImageResizer\src\main !!!!!!!!!!
    //ON RUN set "Working Directory" : MAC     !!!!!!!!!! /Users/macbook/IdeaProjects/ImageScaler/src/main !!!!!!!!!!
    private static void resize(int imgNr, int cat) {
        String imgName = imgNr + ".jpg";
        ImageLoadSaveService imageLoadSaveService = new ImageLoadSaveService();
        BufferedImage image = imageLoadSaveService.load("resize/cat" + cat + "/" + imgName);
        if (image != null) {
            image = Scalr.resize(image, Scalr.Mode.AUTOMATIC, _WIDTH, _HEIGHT);
            imageLoadSaveService.save(image, "resources/resized/cat" + cat + "/" + imgName, "jpg");
            System.out.println("Resized " + cat + "/" + imgNr);
        }
    }
}