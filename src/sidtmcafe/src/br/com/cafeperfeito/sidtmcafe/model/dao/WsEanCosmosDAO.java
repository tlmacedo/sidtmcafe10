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
                    String.format("ncm:%s", jsonObject.getJSONObject("ncm").getString("code")));
        return retorno;
    }

}
