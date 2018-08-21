package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.WsEanCosmosVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaWebService;
import org.json.JSONArray;
import org.json.JSONObject;

public class WsEanCosmosDAO extends ServiceBuscaWebService implements Constants {

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
            retorno += String.format(String.format("descricao=%s", jsonObject.getString("description")));
        if (jsonObject.has("ncm")) {
            if (!retorno.equals("")) retorno += "_";
            retorno += String.format("ncm=%s", jsonObject.getJSONObject("ncm").getString("code"));
        }
        if (jsonObject.has("thumbnail")) {
            if (!retorno.equals("")) retorno += "_";
            retorno += String.format("imgProduto=%s", jsonObject.getString("thumbnail"));
        }
        if (jsonObject.has("barcode_image")) {
            if (!retorno.equals("")) retorno += "_";
            retorno += String.format("imgCodBarra=%s", jsonObject.getString("barcode_image"));
        }
        return retorno;
    }

}
