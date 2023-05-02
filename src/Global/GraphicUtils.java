package Global;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicUtils {

    public static Image resizeImage(Image originalImage, int targetWidth, int targetHeight){
        return originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);

    }
}
