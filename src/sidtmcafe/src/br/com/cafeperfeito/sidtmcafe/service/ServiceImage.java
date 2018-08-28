package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ServiceImage implements Constants {

    public static Image getImagemFromUrl(String strUrl) {
        Image image = null;
        try {
            URL url = new URL(strUrl);
            image = SwingFXUtils.toFXImage(ImageIO.read(url), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return image;
    }

    public static InputStream getInputStreamFromImage(Image image) {
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

    public static Image getImageFromInputStream(InputStream inputStream) {
        try {
            return SwingFXUtils.toFXImage(ImageIO.read(inputStream), null);
        } catch (Exception ex) {
            if (!(ex instanceof NullPointerException))
                ex.printStackTrace();
        }
        return null;
    }

    public static Image getImageCodBarrasEAN13(String busca) {
        Barcode barcode = null;
        try {
            barcode = BarcodeFactory.createEAN13(busca.substring(0, 12));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            return getImageResized(SwingFXUtils.toFXImage(BarcodeImageHandler.getImage(barcode), null));
        } catch (OutputException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getImageResized(Image image) {
        final BufferedImage originalImage = SwingFXUtils.fromFXImage(image, null);
        final BufferedImage resizedImage = new BufferedImage(IMG_PRODUTO_CODBARRA_WIDTH, IMG_PRODUTO_CODBARRA_HEIGHT, originalImage.getType());

        final Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_PRODUTO_CODBARRA_WIDTH, IMG_PRODUTO_CODBARRA_HEIGHT, null);
        g.dispose();
        return SwingFXUtils.toFXImage(resizedImage, null);
    }


}
