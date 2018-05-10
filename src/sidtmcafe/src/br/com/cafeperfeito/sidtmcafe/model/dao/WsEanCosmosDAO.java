package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.WsEanCosmosVO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WsEanCosmosDAO extends BuscaWebService implements Constants {

    JSONObject jsonObject;
    JSONArray jsonArray;

    WsEanCosmosVO wsEanCosmosVO;


    public WsEanCosmosVO getWsEanCosmosVO(String codEan) {
        jsonObject = getJsonObjectHttpUrlConnection(WS_COSMOS_URL + WS_COSMOS_SER_GTINS + codEan + ".json", WS_COSMOS_TOKEN, "");
        if (jsonObject == null)
            return wsEanCosmosVO = null;
        try {
            wsEanCosmosVO = new WsEanCosmosVO();
            wsEanCosmosVO.setDescricao(jsonObject.getString("description"));
            wsEanCosmosVO.setNcm(jsonObject.getJSONObject("ncm").getString("code"));
        } catch (Exception ex) {
            if (!(ex instanceof JSONException))
                ex.printStackTrace();
        }
        return wsEanCosmosVO;
    }

}
