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
        return (jsonObject =  getJsonObjectHttpUrlConnection(WS_COSMOS_URL + WS_COSMOS_SER_GTINS + busca + ".json", WS_COSMOS_TOKEN, ""));
    }

    public WsEanCosmosVO getWsEanCosmosVO(String busca) {
//        if (getRetWs(busca, WS_COSMOS_SER_GTINS) == null)
            if (getRetWs(busca) == null)
            return null;
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

    public void getProdutoNcmCest_EanCosmosVO(TabProdutoVO produtoVO, String busca) {
        if (getWsEanCosmosVO(busca) == null)
            return;
        try {
            produtoVO.setDescricao(jsonObject.getString("description"));
            produtoVO.setCodigo(jsonObject.getJSONObject("ncm").getString("code"));
        } catch (Exception ex) {
            if (!(ex instanceof JSONException))
                ex.printStackTrace();
        }
        return;
    }

}
