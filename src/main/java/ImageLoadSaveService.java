import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.InputStream;

class ImageLoadSaveService {


    BufferedImage load(String name) {
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(ImageLoadSaveService.class.getResourceAsStream(name));
        } catch (Exception ignored) {
            System.out.println("error loading " + name);
            int j = 0;
        }

        return bufferedImage;
    }

    void save(BufferedImage image, String name) {
        try {
            ImageIO.write(image, "png", new FileOutputStream(name));
        } catch (Exception ignored) {
            int j = 0;
        }
    }
}
