package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import javafx.scene.image.Image;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

public class BuscaWebService implements Constants {
    Object wsJsonObjectWebServiceVO;
    String strRetURL;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    String linhaRetorno = null;

    HttpURLConnection urlConnection;

    JSONObject jsonObject;

    public JSONObject getJsonObjectWebService(String strURL) {
        jsonObject = null;
        try {
            wsJsonObjectWebServiceVO = new URL(strURL).openStream();
            jsonObject = new JSONObject(getStringBuilder(wsJsonObjectWebServiceVO).toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jsonObject;
    }

    public String getObjectWebService(String strURL) {
        strRetURL = null;
        try {
            wsJsonObjectWebServiceVO = new URL(strURL).openStream();
            return strRetURL = getStringBuilder(wsJsonObjectWebServiceVO).toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return strRetURL;
    }

    public JSONObject getJsonObjectHttpUrlConnection(String strURL, String token, String compl) {
        jsonObject = null;
        try {
            urlConnection = (HttpURLConnection) new URL(strURL).openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            if (strURL.contains("cosmos")) {
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("X-Cosmos-Token", token);
            } else {
                urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            }
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                wsJsonObjectWebServiceVO = urlConnection.getInputStream();
                jsonObject = new JSONObject(getStringBuilder(wsJsonObjectWebServiceVO).toString());
            }
        } catch (SocketTimeoutException ex) {
            try {
                urlConnection = (HttpURLConnection) new URL(strURL + compl).openConnection();
                urlConnection.setConnectTimeout(40000);
                urlConnection.setReadTimeout(40000);
                if (strURL.contains("cosmos")) {
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("X-Cosmos-Token", token);
                } else {
                    urlConnection.setRequestProperty("Authorization", "Bearer " + token);
                }
                urlConnection.connect();
                wsJsonObjectWebServiceVO = urlConnection.getInputStream();
                if (urlConnection.getResponseCode() == 200)
                    jsonObject = new JSONObject(getStringBuilder(wsJsonObjectWebServiceVO));
            } catch (Exception ex1) {
                if (!(ex1 instanceof TimeoutException))
                    ex1.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    StringBuilder getStringBuilder(Object retorno) {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader((InputStream) retorno, "UTF-8"));
            stringBuilder = new StringBuilder();
            while ((linhaRetorno = bufferedReader.readLine()) != null) {
                System.out.printf("BuscaWebService.getStringBuilder ===>> %s\n", linhaRetorno);
                stringBuilder.append(linhaRetorno);
            }
        } catch (Exception ex) {
        }
        return stringBuilder;
    }

    public static void getImagem(String strUrl, String nomeArquivo) throws IOException {
        String saveAs = String.format("%s%s%s",PATH_IMAGE_DOWNLOAD, nomeArquivo, TYPE_IMAGE_DOWNLOAD);
        InputStream in = new URL(strUrl).openStream();
        try {
            Files.copy(in, Paths.get(saveAs));
        } catch (Exception ex) {
            if (ex instanceof FileAlreadyExistsException) {
                Files.delete(Paths.get(saveAs));
                Files.copy(in, Paths.get(saveAs));
            }
        }

//        URL url = new URL(strUrl);
//        File file = new File(String.format("%s/%s.%s", PATH_IMAGE_DOWNLOAD, saveAs, type));
//
//        InputStream is = url.openStream();
//        FileOutputStream fos = new FileOutputStream(file);
//
//        int bytes = 0;
//
//        while ((bytes = is.read()) != -1) {
//            fos.write(bytes);
//        }
//
//        is.close();
//        fos.close();


    }

}
