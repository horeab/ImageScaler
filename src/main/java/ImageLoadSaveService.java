import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

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

    void save(BufferedImage image, String name, String ext) {
        try {
            ImageIO.write(image, ext, new FileOutputStream(name));
        } catch (Exception ignored) {
            System.out.println("error saving " + name);
            int j = 0;
        }
    }
}
