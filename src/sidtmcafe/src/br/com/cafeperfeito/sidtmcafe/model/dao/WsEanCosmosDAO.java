package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoCodBarraVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.WsEanCosmosVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaWebService;
import br.com.cafeperfeito.sidtmcafe.service.ServiceEan13;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import br.com.cafeperfeito.sidtmcafe.service.ServiceImage;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class WsEanCosmosDAO extends ServiceBuscaWebService implements Constants {

    JSONObject jsonObject;
    JSONArray jsonArray;

    WsEanCosmosVO wsEanCosmosVO;
    String retorno = "";

    JSONObject getRetWs(String busca) {
        return (jsonObject = getJsonObjectHttpUrlConnection(WS_COSMOS_URL + WS_COSMOS_SER_GTINS + busca + ".json", WS_COSMOS_TOKEN, ""));
    }

    public String getWsEanCosmosVO(TabProdutoVO produto, String busca) {
        if (getRetWs(busca) != null) {
            if (jsonObject.has("description"))
                retorno += "descricao::" + jsonObject.getString("description") + ";";
            if (jsonObject.has("ncm"))
                retorno += "ncm::" + jsonObject.getJSONObject("ncm").getString("code") + ";";
            if (jsonObject.has("cest"))
                retorno += "cest::" + jsonObject.getJSONObject("cest").getString("code") + ";";
            if (jsonObject.has("thumbnail"))
                retorno += "imgProduto::" + jsonObject.getString("thumbnail") + ";";
        }
        getInforcacoesProduto(produto, busca);

        return retorno;
    }

    void getInforcacoesProduto(TabProdutoVO produto, String busca) {
        Image imageTmp[] = new Image[2];
        if (produto.getImgProduto() != null)
            produto.setImgProdutoBack(produto.getImgProduto());
        imageTmp[0] = SIS_PRODUTO_IMG_DEFAULT;
        imageTmp[1] = new ServiceEan13(busca).createBarcodePNG();
        if (!retorno.equals("")) {
            HashMap hashMap = ServiceFormatarDado.getFieldFormatMap(retorno);
            if (hashMap.containsKey("descricao"))
                produto.setDescricao(hashMap.get("descricao").toString());
            if (hashMap.containsKey("ncm"))
                produto.setNcm(hashMap.get("ncm").toString());
            if (hashMap.containsKey("cest"))
                produto.setCest(hashMap.get("cest").toString());
            if (hashMap.containsKey("imgProduto"))
                if ((imageTmp[0] = ServiceImage.getImagemFromUrl(hashMap.get("imgProduto").toString())) == null)
                    imageTmp[0] = SIS_PRODUTO_IMG_DEFAULT;
        } else {
            retorno = "";
        }
        produto.setImgProduto(imageTmp[0]);
        if (produto.getCodBarraVOList().stream()
                .filter(cod -> cod.getCodBarra().equals(busca))
                .count() == 0)
            produto.getCodBarraVOList().add(new TabProdutoCodBarraVO(busca, imageTmp[1]));
    }

}


