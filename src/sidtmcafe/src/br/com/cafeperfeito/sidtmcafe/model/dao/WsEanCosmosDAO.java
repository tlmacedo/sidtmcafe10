package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.WsEanCosmosVO;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.text.Style;

public class WsEanCosmosDAO extends BuscaWebService implements Constants {

    JSONObject jsonObject;
    JSONArray jsonArray;

    WsEanCosmosVO wsEanCosmosVO;
    String retorno = "", tmp = "";

    JSONObject getRetWs(String busca) {
        return (jsonObject = getJsonObjectHttpUrlConnection(WS_COSMOS_URL + WS_COSMOS_SER_GTINS + busca + ".json", WS_COSMOS_TOKEN, ""));
    }

    public String getWsEanCosmosVO(String busca) {
        if (getRetWs(busca) == null)
            return retorno;
        if (jsonObject.has("description"))
            retorno += String.format(String.format("descricao:%s", jsonObject.getString("description")));
        if (jsonObject.has("ncm"))
            retorno += String.format("%sncm:%s", retorno.equals("") ? "" : ", ",
                    String.format("%s", jsonObject.getJSONObject("ncm").getString("code")));
        try {
            if (jsonObject.has("thumbnail"))
                getImagem(jsonObject.getString("thumbnail"), "7896078301063", "jpg");
            if (jsonObject.has("barcode_image"))
                getImagem(jsonObject.getString("barcode_image"), "7896078301063" + "_barcode", "jpg");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retorno;
    }

}
