package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import javafx.embed.swing.SwingFXUtils;
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

public class ServiceBuscaWebService implements Constants {
    public Object wsJsonObjectWebServiceVO;
    public String strRetURL;
    public BufferedReader bufferedReader;
    public StringBuilder stringBuilder;
    public String linhaRetorno = null;

    public HttpURLConnection urlConnection;

    public JSONObject jsonObject;

    public JSONObject getJsonObjectWebService(String strUrl) {
        jsonObject = null;
        try {
            wsJsonObjectWebServiceVO = new URL(strUrl).openStream();
            jsonObject = new JSONObject(getStringBuilder(wsJsonObjectWebServiceVO).toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jsonObject;
    }

    public String getObjectWebService(String strUrl) {
        strRetURL = null;
        try {
            wsJsonObjectWebServiceVO = new URL(strUrl).openStream();
            return strRetURL = getStringBuilder(wsJsonObjectWebServiceVO).toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return strRetURL;
    }

    public JSONObject getJsonObjectHttpUrlConnection(String strUrl, String token, String compl) {
        jsonObject = null;
        try {
            urlConnection = (HttpURLConnection) new URL(strUrl).openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            if (strUrl.contains("cosmos")) {
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("X-Cosmos-Token", token);
            } else {
                urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            }
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                wsJsonObjectWebServiceVO = urlConnection.getInputStream();
                jsonObject = new JSONObject(getStringBuilder(wsJsonObjectWebServiceVO).toString());
            } else {
                System.out.println("urlConnection.getResponseCode(1): [" + urlConnection.getResponseCode() + "]");
            }
        } catch (SocketTimeoutException ex) {
            try {
                urlConnection = (HttpURLConnection) new URL(strUrl + compl).openConnection();
                urlConnection.setConnectTimeout(40000);
                urlConnection.setReadTimeout(40000);
                if (strUrl.contains("cosmos")) {
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("X-Cosmos-Token", token);
                } else {
                    urlConnection.setRequestProperty("Authorization", "Bearer " + token);
                }
                urlConnection.connect();
                wsJsonObjectWebServiceVO = urlConnection.getInputStream();
                if (urlConnection.getResponseCode() == 200) {
                    jsonObject = new JSONObject(getStringBuilder(wsJsonObjectWebServiceVO));
                } else {
                    System.out.println("urlConnection.getResponseCode(2): [" + urlConnection.getResponseCode() + "]");
                }
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
                System.out.printf("ServiceBuscaWebService.getStringBuilder ===>> %s\n", linhaRetorno);
                stringBuilder.append(linhaRetorno);
            }
        } catch (Exception ex) {
        }
        return stringBuilder;
    }

    public static Image getImagem(String strUrl) {
        Image image = null;
        try {
            URL url = new URL(strUrl);
            image = SwingFXUtils.toFXImage(ImageIO.read(url), null);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return image;


        /*
        InputStream in = null;
        try {
            urlConnection = (HttpURLConnection) new URL(strUrl).openConnection();
            urlConnection.connect();
            in = urlConnection.getInputStream();
            Image image = new Image(String.valueOf(ImageIO.read(in)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        */

        /*
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
         */


    }
}
