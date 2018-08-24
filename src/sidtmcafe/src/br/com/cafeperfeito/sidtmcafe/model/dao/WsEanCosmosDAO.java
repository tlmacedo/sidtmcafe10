package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProduto_CodBarraVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.WsEanCosmosVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaWebService;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
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
        if (getRetWs(busca) == null)
            return retorno;
        if (jsonObject.has("description"))
            retorno += String.format(String.format("descricao::%s;", jsonObject.getString("description")));
        if (jsonObject.has("ncm"))
            retorno += String.format("ncm::%s;", jsonObject.getJSONObject("ncm").getString("code"));
        if (jsonObject.has("thumbnail"))
            retorno += String.format("imgProduto::%s;", jsonObject.getString("thumbnail"));
        if (jsonObject.has("barcode_image"))
            retorno += String.format("imgCodBarra::%s;", jsonObject.getString("barcode_image"));
        if (produto != null)
            getProdutoEanCosmosVO(produto, busca);
        return retorno;
    }

    void getProdutoEanCosmosVO(TabProdutoVO produto, String busca) {
        HashMap hashMap = ServiceFormatarDado.getFieldFormatMap(retorno);
        if (hashMap.containsKey("descricao"))
            produto.setDescricao(hashMap.get("descricao").toString());
        if (hashMap.containsKey("ncm"))
            produto.setNcm(hashMap.get("ncm").toString());
        Image imageTmp = null;
        if (hashMap.containsKey("imgProduto")) {
            if (produto.getImgProduto() != null)
                produto.setImgProdutoBack(produto.getImgProduto());
            if ((imageTmp = ServiceBuscaWebService.getImagem(hashMap.get("imgProduto").toString())) == null) ;
            imageTmp = ServiceVariavelSistema.IMG_PRODUTO_DEFAULT;
            produto.setImgProduto(imageTmp);
        }
        imageTmp=null;
        if (hashMap.containsKey("imgCodBarra")) {
            imageTmp = ServiceBuscaWebService.getImagem(hashMap.get("imgCodBarra").toString());
        }
        produto.getCodBarraVOList().add(new TabProduto_CodBarraVO(busca, imageTmp));
    }
}


