package br.com.cafeperfeito.sidtmcafe.teste;

import br.com.cafeperfeito.sidtmcafe.service.ServiceCryptografia;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static br.com.cafeperfeito.sidtmcafe.interfaces.Constants.*;

public class Testes {

    public static void main(String... args) throws Exception {

//        String senhaSimples = "cafeperfeito";
//        System.out.println("senhaSimples: [" + senhaSimples + "]");
//
//        String originalString = "4879";
//        System.out.println("Original String to encrypt - " + originalString);
//        String encryptedString = ServiceCryptografia.encrypt(originalString);
//        System.out.println("Encrypted String - " + encryptedString);
//        String decryptedString = ServiceCryptografia.decrypt(encryptedString);
//        System.out.println("After decryption - " + decryptedString);

        //String key = "DB99A2A8EB6904F492E9DF0595ED683C";
        //String password = "Admin";

//        String salt ="cafeperfeito.com";// ServiceCryptografia.gerarSenhaSalt();
//        Scanner scan = new Scanner(System.in);
////        System.out.println("Please Enter Key:");
//        //key = key;//scanner.next();
//        System.out.println("Please Enter Plain Text Password:");
//        String senha = scan.next();
//        String encrypt = ServiceCryptografia.encrypt(senha,salt);
//        System.out.println("SenhaEncrypt: " + encrypt);
//        String decrypt = ServiceCryptografia.decrypt(encrypt,salt);
//        System.out.println("SenhaDecrypt: " + decrypt);
//
//
//
//        String key;
//        KeyGenerator keyGen = KeyGenerator.getInstance(ServiceCryptografia.AES);
//        keyGen.init(128);
//        SecretKey sk = keyGen.generateKey();
//        key = ServiceCryptografia.byteArrayToHexString(sk.getEncoded());
//        System.out.println("key:" + key);
//        key = ServiceCryptografia.gerarSenhaSalt();
//        System.out.println("key:" + key);
//        Scanner scanner = new Scanner(System.in);
////        System.out.println("Please Enter Key:");
//        //key = key;//scanner.next();
//        System.out.println("Please Enter Plain Text Password:");
//        String password = scanner.next();
//
//        byte[] bytekey = hexStringToByteArray(key);
//        SecretKeySpec sks = new SecretKeySpec(bytekey, ServiceCryptografia.AES);
//        Cipher cipher = Cipher.getInstance(ServiceCryptografia.AES);
//        cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
//        byte[] encrypted = cipher.doFinal(password.getBytes());
//        String encryptedpwd = ServiceCryptografia.byteArrayToHexString(encrypted);
//        System.out.println("****************  Encrypted Password  ****************");
//        System.out.println(encryptedpwd);
//        System.out.println("****************  Encrypted Password  ****************");
//
//
//        String tempkey = key;
//        password = encryptedpwd;
//
//        bytekey = hexStringToByteArray(tempkey);
//        sks = new SecretKeySpec(bytekey, ServiceCryptografia.AES);
//        cipher = Cipher.getInstance(ServiceCryptografia.AES);
//        cipher.init(Cipher.DECRYPT_MODE, sks);
//        byte[] decrypted = cipher.doFinal(hexStringToByteArray(password));
//        String OriginalPassword = new String(decrypted);
//        System.out.println("****************  Original Password  ****************");
//        System.out.println(OriginalPassword);
//        System.out.println("****************  Original Password  ****************");
//
//
//        String ean = "7898903647025", path = System.getProperty("user.home") + "/Pictures/" + ean + "_EAN.png";

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


//        new ServiceEan13(ean).createBarcodePNG();


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


//        System.out.println("cnpj: [" + new ServiceFormatarDado().gerarMascara("len::;cnpj::;") + "]");
//        System.out.println("cpf: [" + new ServiceFormatarDado().gerarMascara("cpf") + "]");
//        System.out.println("barcode: [" + new ServiceFormatarDado().gerarMascara("barcode") + "]");
//        System.out.println("4numero0(123456): [" + new ServiceFormatarDado().gerarMascara("4numero0") + "]");
//        System.out.println("peso3(123456): [" + new ServiceFormatarDado().gerarMascara("peso3") + "]");
//        System.out.println("moeda2(123456): [" + new ServiceFormatarDado().gerarMascara("moeda2") + "]");
//        System.out.println("moeda3(123456): [" + new ServiceFormatarDado().gerarMascara("moeda3") + "]");
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
//        System.out.println("numero0(123456): [" + new ServiceFormatarDado().getValorFormatado("123456","6numero0") + "]");
//        System.out.println("peso3(123456): [" + new ServiceFormatarDado().getValorFormatado("123456","peso3") + "]");
//        System.out.println("moeda2(123456): [" + new ServiceFormatarDado().getValorFormatado("12345678","moeda2") + "]");
//        System.out.println("moeda3(123456): [" + new ServiceFormatarDado().getValorFormatado("12345678","moeda3") + "]");
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



        //System.out.println("moeda2(123456): [" + new ServiceFormatarDado().getValorFormatado("1234567890123",15,"moeda", 3) + "]");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date()));
        String date = "2017-03-08T12:30:54";
        LocalDateTime localDateTime1 = LocalDateTime.parse(date);
        System.out.println("origional date as string: " + date);
        System.out.println("generated LocalDateTime: " + localDateTime1);

        String dhEmi = "2018-09-03T12:01:12-03:00";//.replace("T", " ");//"YYYY-MM-ddTHH:mm:ssGTM"
        //System.out.println("localDateTime: [" + SDF.parse(dhEmi) + "]");
        LocalDateTime localDateTime = LocalDateTime.parse(dhEmi, DTF_NFE_TO_LOCAL_DATE);
        System.out.println("localDateTime: [" + localDateTime + "]");
        System.out.println("toLocalDate: [" + localDateTime.toLocalDate() + "]");



    }


}
