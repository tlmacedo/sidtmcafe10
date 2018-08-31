package br.com.cafeperfeito.sidtmcafe.teste;

import br.com.cafeperfeito.sidtmcafe.service.ServiceEan13;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.nepxion.zxing.core.ZxingDecoder;
import com.nepxion.zxing.core.ZxingEncoder;
import com.nepxion.zxing.entity.ZxingEntity;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Testes {

    public static void main(String... args) {
        String ean = "7898903647025", path = System.getProperty("user.home") + "/Pictures/" + ean + "_EAN.png";

//        String text = "www.cafeperfeito.com.br";
//        // 二维码图片导出路径
//        //File file = new File("E:/二维码.jpg");
//        File file = new File(path);
//
//        // 二维码参数的构造对象，很多参数赋予了默认值，可自行通过set方法更改
//        ZxingEntity entity = new ZxingEntity();
//        entity.setBarcodeFormat(BarcodeFormat.QR_CODE);
//        entity.setText(text);
//        entity.setOutputFile(file);
//        entity.setWidth(300);
//        entity.setHeight(300);
//
//        // 以文件格式读取并导出，该方式适合本地调用
//        ZxingEncoder encoder = new ZxingEncoder();
//        encoder.encodeForFile(entity);
//
//        // 以文件格式扫描并解析
//        ZxingDecoder decoder = new ZxingDecoder();
//        Result result = decoder.decodeByFile(file, entity.getEncoding());
//
//        System.out.println("扫描结果 - [Text] : " + result.getText() + " [Timestamp] : " + result.getTimestamp() + " [BarcodeFormat] : " + result.getBarcodeFormat() + " [NumBits] : " + result.getNumBits());


//        // 条形码内容
//        String text = ean;//"6943620593115";
//        // 条形码图片导出路径
//        File file = new File(path);
//
//        // 条形码参数的构造对象，很多参数赋予了默认值，可自行通过set方法更改
//        ZxingEntity entity = new ZxingEntity();
//        entity.setBarcodeFormat(BarcodeFormat.EAN_13);
//        entity.setText(text);
//        entity.setMargin(5);
//        entity.setOutputFile(file);
//        entity.setWidth(115);
//        entity.setHeight(40);
//
//        // 以文件格式读取并导出，该方式适合本地调用
//        ZxingEncoder encoder = new ZxingEncoder();
//        encoder.encodeForFile(entity);
//
//        // 以文件格式扫描并解析
//        ZxingDecoder decoder = new ZxingDecoder();
//        Result result = decoder.decodeByFile(file, entity.getEncoding());
//
//        System.out.println("扫描结果 - [Text] : " + result.getText() + " [Timestamp] : " + result.getTimestamp() + " [BarcodeFormat] : " + result.getBarcodeFormat() + " [NumBits] : " + result.getNumBits());





//        String text = "123456789101";
//
//        int width = 300;
//        int height = 100;
//        String imgFormat = "png";
//        try {
//            BitMatrix bitMatrix = new EAN13Writer().encode(ean, BarcodeFormat.EAN_13, width, height);
//            MatrixToImageWriter.writeToStream(bitMatrix, imgFormat, new FileOutputStream(new File(path)));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Success!");


        new ServiceEan13(ean).createBarcodePNG();


//        try{
//            Barcode barcode = BarcodeFactory.createEAN13(ean);
//            BufferedImage image = new BufferedImage(220, 130, BufferedImage.TYPE_BYTE_GRAY);
//            Graphics2D g = (Graphics2D) image.getGraphics();
//            g.setBackground(Color.BLUE);
//            barcode.draw(g, 10, 56);
//            File f = new File(path);
//            // Let the barcode image handler do the hard work
//            BarcodeImageHandler.saveJPEG(barcode, f);
//        }catch(Exception ex){
//            ex.getMessage();
//        }




//        Barcode barcode = null;
//        try {
//            barcode = BarcodeFactory.createEAN13(ean.substring(0, 12));
//            ImageIO.write(BarcodeImageHandler.getImage(barcode), "PNG", new File(path));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }


//        System.out.println("cnpj: [" + new ServiceFormatarDado().gerarMascara("cnpj") + "]");
//        System.out.println("cpf: [" + new ServiceFormatarDado().gerarMascara("cpf") + "]");
//        System.out.println("barcode: [" + new ServiceFormatarDado().gerarMascara("barcode") + "]");
//        System.out.println("6numero0(123456): [" + new ServiceFormatarDado().gerarMascara("6numero0") + "]");
//        System.out.println("6peso3(123456): [" + new ServiceFormatarDado().gerarMascara("6peso3") + "]");
//        System.out.println("8moeda2(123456): [" + new ServiceFormatarDado().gerarMascara("8moeda2") + "]");
//        System.out.println("8moeda3(123456): [" + new ServiceFormatarDado().gerarMascara("8moeda3") + "]");
//        System.out.println("cep(69067360): [" + new ServiceFormatarDado().gerarMascara("cep") + "]");
//        System.out.println("ncm(09012100): [" + new ServiceFormatarDado().gerarMascara("nfencm") + "]");
//        System.out.println("cest(1234567): [" + new ServiceFormatarDado().gerarMascara("nfecest") + "]");
//        System.out.println("nfechave: [" + new ServiceFormatarDado().gerarMascara("nfechave") + "]");
//        System.out.println("nfenumero: [" + new ServiceFormatarDado().gerarMascara("nfenumero") + "]");
//        System.out.println("nfedocorigem: [" + new ServiceFormatarDado().gerarMascara("nfedocorigem") + "]");
//        System.out.println("telefone8: [" + new ServiceFormatarDado().gerarMascara("telefone8") + "]");
//        System.out.println("telefone9: [" + new ServiceFormatarDado().gerarMascara("telefone9") + "]");
//        System.out.println("ie(ac): [" + new ServiceFormatarDado().gerarMascara("ieac") + "]");
//        System.out.println("ie(al): [" + new ServiceFormatarDado().gerarMascara("ieal") + "]");
//        System.out.println("ie(am): [" + new ServiceFormatarDado().gerarMascara("ieam") + "]");
//        System.out.println("ie(ap): [" + new ServiceFormatarDado().gerarMascara("ieap") + "]");
//        System.out.println("ie(ba): [" + new ServiceFormatarDado().gerarMascara("ieba") + "]");
//        System.out.println("ie(ce): [" + new ServiceFormatarDado().gerarMascara("iece") + "]");
//        System.out.println("ie(df): [" + new ServiceFormatarDado().gerarMascara("iedf") + "]");
//        System.out.println("ie(es): [" + new ServiceFormatarDado().gerarMascara("iees") + "]");
//        System.out.println("ie(go): [" + new ServiceFormatarDado().gerarMascara("iego") + "]");
//        System.out.println("ie(ma): [" + new ServiceFormatarDado().gerarMascara("iema") + "]");
//        System.out.println("ie(mg): [" + new ServiceFormatarDado().gerarMascara("iemg") + "]");
//        System.out.println("ie(ms): [" + new ServiceFormatarDado().gerarMascara("iems") + "]");
//        System.out.println("ie(mt): [" + new ServiceFormatarDado().gerarMascara("iemt") + "]");
//        System.out.println("ie(pa): [" + new ServiceFormatarDado().gerarMascara("iepa") + "]");
//        System.out.println("ie(pb): [" + new ServiceFormatarDado().gerarMascara("iepb") + "]");
//        System.out.println("ie(pe): [" + new ServiceFormatarDado().gerarMascara("iepe") + "]");
//        System.out.println("ie(pi): [" + new ServiceFormatarDado().gerarMascara("iepi") + "]");
//        System.out.println("ie(pr): [" + new ServiceFormatarDado().gerarMascara("iepr") + "]");
//        System.out.println("ie(rj): [" + new ServiceFormatarDado().gerarMascara("ierj") + "]");
//        System.out.println("ie(rn): [" + new ServiceFormatarDado().gerarMascara("iern") + "]");
//        System.out.println("ie(ro): [" + new ServiceFormatarDado().gerarMascara("iero") + "]");
//        System.out.println("ie(rr): [" + new ServiceFormatarDado().gerarMascara("ierr") + "]");
//        System.out.println("ie(rs): [" + new ServiceFormatarDado().gerarMascara("iers") + "]");
//        System.out.println("ie(sc): [" + new ServiceFormatarDado().gerarMascara("iesc") + "]");
//        System.out.println("ie(se): [" + new ServiceFormatarDado().gerarMascara("iese") + "]");
//        System.out.println("ie(sp): [" + new ServiceFormatarDado().gerarMascara("iesp") + "]");
//        System.out.println("ie(to): [" + new ServiceFormatarDado().gerarMascara("ieto") + "]");
//
//        System.out.println("cnpj(08009246000136): [" + new ServiceFormatarDado().getValorFormatado("08009246000136", "cnpj") + "]");
//        System.out.println("cpf(52309550230): [" + new ServiceFormatarDado().getValorFormatado("52309550230", "cpf") + "]");
//        System.out.println("barcode(7896423421255): [" + new ServiceFormatarDado().getValorFormatado("7896423421255", "barcode") + "]");
//        System.out.println("6numero0(123456): [" + new ServiceFormatarDado().getValorFormatado("123456","6numero0") + "]");
//        System.out.println("6peso3(123456): [" + new ServiceFormatarDado().getValorFormatado("123456","6peso3") + "]");
//        System.out.println("8moeda2(123456): [" + new ServiceFormatarDado().getValorFormatado("12345678","8moeda2") + "]");
//        System.out.println("8moeda3(123456): [" + new ServiceFormatarDado().getValorFormatado("12345678","8moeda3") + "]");
//        System.out.println("cep(69067360): [" + new ServiceFormatarDado().getValorFormatado("69067360","cep") + "]");
//        System.out.println("ncm(09012100): [" + new ServiceFormatarDado().getValorFormatado("09012100","nfencm") + "]");
//        System.out.println("cest(1234567): [" + new ServiceFormatarDado().getValorFormatado("1234567","nfecest") + "]");
//        System.out.println("nfechave(35180406981833000248550010000000471027609712): [" + new ServiceFormatarDado().getValorFormatado("35180406981833000248550010000000471027609712", "nfechave") + "]");
//        System.out.println("nfenumero(000000047): [" + new ServiceFormatarDado().getValorFormatado("000000047", "nfenumero") + "]");
//        System.out.println("nfedocorigem(041812441652): [" + new ServiceFormatarDado().getValorFormatado("041812441652", "nfedocorigem") + "]");
//        System.out.println("telefone8(38776148): [" + new ServiceFormatarDado().getValorFormatado("38776148", "telefone8") + "]");
//        System.out.println("telefone9(981686148): [" + new ServiceFormatarDado().getValorFormatado("981686148", "telefone9") + "]");
//
//        System.out.println("ie(AM): [" + new ServiceFormatarDado().gerarMascara("ieam") + "]");
//
//
//        System.out.print("digite um valor para conversão: ");
//
//
//


    }


}
