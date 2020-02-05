import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.InputStream;

class ImageLoadSaveService {


    BufferedImage load(String name) {
        BufferedImage i = null;

        try {
            i = ImageIO.read(ImageLoadSaveService.class.getResourceAsStream(name));
        } catch (Exception ignored) {
            int j = 0;
        }

        return i;
    }

    void save(BufferedImage image, String name) {
        try {
            ImageIO.write(image, "PNG", new FileOutputStream(name));
        } catch (Exception ignored) {
            int j = 0;
        }
    }
}
