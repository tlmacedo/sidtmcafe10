package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.file.FileStore;
import java.nio.file.Files;

public class ServiceImage implements Constants {

    public static InputStream getInputStream(Image image) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] res = outputStream.toByteArray();
            return new ByteArrayInputStream(res);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Image getImage(InputStream inputStream) {
        try {
            return SwingFXUtils.toFXImage(ImageIO.read(inputStream), null);
        } catch (Exception ex) {
            if (!(ex instanceof NullPointerException))
                ex.printStackTrace();
        }
        return null;
    }


}
