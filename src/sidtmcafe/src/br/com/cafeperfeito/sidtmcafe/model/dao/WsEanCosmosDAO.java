package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.WsEanCosmosVO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WsEanCosmosDAO extends BuscaWebService implements Constants {

    JSONObject jsonObject;
    JSONArray jsonArray;

    WsEanCosmosVO wsEanCosmosVO;

    JSONObject getRetWs(String busca) {
        return (jsonObject = getJsonObjectHttpUrlConnection(WS_COSMOS_URL + WS_COSMOS_SER_GTINS + busca + ".json", WS_COSMOS_TOKEN, ""));
    }

    public void getWsEanCosmosVO(String busca) {
        if (getRetWs(busca) == null)
            return;
        try {
            wsEanCosmosVO = new WsEanCosmosVO();
            wsEanCosmosVO.setDescricao(jsonObject.getString("description"));
            wsEanCosmosVO.setNcm(jsonObject.getJSONObject("ncm").getString("code"));
        } catch (Exception ex) {
            if (!(ex instanceof JSONException))
                ex.printStackTrace();
        }
    }

    public String getStringNcmProduto_EanCosmosVO(TabProdutoVO produtoVO, String busca) {
        getWsEanCosmosVO(busca);
        if (wsEanCosmosVO == null)
            return "";
        produtoVO.setDescricao(wsEanCosmosVO.getDescricao());
        return wsEanCosmosVO.getNcm();
    }

}
