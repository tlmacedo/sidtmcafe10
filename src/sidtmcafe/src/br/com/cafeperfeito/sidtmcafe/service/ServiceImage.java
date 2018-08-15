package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileStore;
import java.nio.file.Files;

public class ServiceImage implements Constants {

    public static byte[] getImgToByte(File imagemPng) {
        try {
            BufferedImage image = ImageIO.read(imagemPng);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage getByteToImg(byte[] imgByte) {
        try {
            InputStream inputStream = new ByteArrayInputStream(imgByte);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
