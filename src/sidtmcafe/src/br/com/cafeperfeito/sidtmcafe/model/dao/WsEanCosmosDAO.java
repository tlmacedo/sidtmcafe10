package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProdutoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabProduto_CodBarraVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.WsEanCosmosVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaWebService;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
            if (jsonObject.has("thumbnail"))
                retorno += "imgProduto::" + jsonObject.getString("thumbnail") + ";";
            if (jsonObject.has("barcode_image"))
                retorno += "imgCodBarra::" + jsonObject.getString("barcode_image") + ";";
        }
        getInforcacoesProduto(produto, busca);

        return retorno;
    }

    void getInforcacoesProduto(TabProdutoVO produto, String busca) {
        Image imageTmp[] = new Image[2];
        if (produto.getImgProduto() != null)
            produto.setImgProdutoBack(produto.getImgProduto());
        if (retorno != null) {
            HashMap hashMap = ServiceFormatarDado.getFieldFormatMap(retorno);
            if (hashMap.containsKey("descricao"))
                produto.setDescricao(hashMap.get("descricao").toString());
            if (hashMap.containsKey("ncm"))
                produto.setNcm(hashMap.get("ncm").toString());
            if (hashMap.containsKey("imgProduto"))
                if ((imageTmp[0] = ServiceBuscaWebService.getImagem(hashMap.get("imgProduto").toString())) == null)
                    imageTmp[0] = IMG_DEFAULT_PRODUTO;
            if (hashMap.containsKey("imgCodBarra")) {
                if ((imageTmp[1] = ServiceBuscaWebService.getImagem(hashMap.get("imgCodBarra").toString())) == null)
                    imageTmp[1] = getCodBarrasEAN(busca);
            }
        } else {
            retorno = "";
            imageTmp[0] = IMG_DEFAULT_PRODUTO;
            imageTmp[1] = getCodBarrasEAN(busca);
        }
        produto.setImgProduto(imageTmp[0]);
        produto.getCodBarraVOList().add(new TabProduto_CodBarraVO(busca, imageTmp[1]));
    }

    Image getCodBarrasEAN(String busca) {
        Barcode barcode = null;
        try {
            barcode = BarcodeFactory.createEAN13(busca.substring(0, 12));
        } catch (Exception ex) {
            if (ex instanceof StringIndexOutOfBoundsException) {
                try {
                    barcode = BarcodeFactory.create3of9(busca.substring(0, 8), true);
                } catch (Exception ex1) {
                    ex1.printStackTrace();
                }
            } else {
                ex.printStackTrace();
            }
        }
        try {
            return getResizeImage(BarcodeImageHandler.getImage(barcode));
            //return SwingFXUtils.toFXImage(BarcodeImageHandler.getImage(barcode), null);
        } catch (OutputException e) {
            e.printStackTrace();
        }
        return null;
    }

    Image getResizeImage(final BufferedImage originalImage) {
        final int originalHeight = originalImage.getHeight();

        final BufferedImage resizedImage = new BufferedImage(228, originalHeight, originalImage.getType());

        final Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 228, originalHeight, null);
        g.dispose();
        return SwingFXUtils.toFXImage(resizedImage, null);
    }
}


