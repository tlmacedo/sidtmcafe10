package br.com.cafeperfeito.sidtmcafe.model.dao;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.TimeoutException;

public class BuscaWebService {
    Object wsCnpjReceitaWsVO;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    String linhaRetorno = null;

    HttpURLConnection urlConnection;

    JSONObject jsonObject;

    public JSONObject getJsonObjectWebService(String strURL) {
        jsonObject = null;
        try {
            wsCnpjReceitaWsVO = new URL(strURL).openStream();
            jsonObject = new JSONObject(getStringBuilder(wsCnpjReceitaWsVO).toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return jsonObject;
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
            wsCnpjReceitaWsVO = urlConnection.getInputStream();
            if (urlConnection.getResponseCode() == 200)
                jsonObject = new JSONObject(getStringBuilder(wsCnpjReceitaWsVO).toString());
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
                wsCnpjReceitaWsVO = urlConnection.getInputStream();
                if (urlConnection.getResponseCode() == 200)
                    jsonObject = new JSONObject(getStringBuilder(wsCnpjReceitaWsVO));
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
//                System.out.printf("BuscaWebService.getStringBuilder ===>> %s\n", linhaRetorno);
                stringBuilder.append(linhaRetorno);
            }
        } catch (Exception ex) {
        }
        return stringBuilder;
    }

}
