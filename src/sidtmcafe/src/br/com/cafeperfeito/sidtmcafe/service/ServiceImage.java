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
            return SwingFXUtils.toFXImage(BarcodeImageHandler.getImage(barcode), null);
        } catch (OutputException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getImageResized(final Image image, String typeImage) {
        int width = 0, height = 0;
        switch (typeImage) {
            case "ean":
            case "barcode":
            case "barras":
            case "codbarra":
            case "codbarras":
            case "codigobarra":
            case "codigobarras":
                width = IMG_PRODUTO_CODBARRA_WIDTH;
                height = IMG_PRODUTO_CODBARRA_HEIGHT;
                break;
            case "produto":
                width = IMG_PRODUTO_IMAGE_WIDTH;
                height = IMG_PRODUTO_IMAGE_HEIGHT;
                break;
        }

        BufferedImage inputImage = SwingFXUtils.fromFXImage(image, null);
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, width, height, null);
        g2d.dispose();
        return SwingFXUtils.toFXImage(outputImage, null);

//        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        final Graphics2D graphics2D = bufferedImage.createGraphics();
//        graphics2D.setComposite(AlphaComposite.Src);
//        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        graphics2D.drawImage(SwingFXUtils.fromFXImage(image, null), 0, 0, width, height, null);
//        graphics2D.dispose();
//        return SwingFXUtils.toFXImage(bufferedImage, null);
//
    }


}
