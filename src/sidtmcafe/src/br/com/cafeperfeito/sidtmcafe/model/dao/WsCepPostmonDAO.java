package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEnderecoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.WsCepPostmonVO;
import org.json.JSONObject;


public class WsCepPostmonDAO extends BuscaWebService implements Constants {

    JSONObject jsonObject;

    WsCepPostmonVO wsCepPostmonVO;

    public WsCepPostmonVO getCepPostmonVO(String cep) {
        jsonObject = getJsonObjectWebService(WS_POSTMON_URL + cep);

        if (jsonObject == null)
            return wsCepPostmonVO = null;

        try {
            wsCepPostmonVO = new WsCepPostmonVO();

            wsCepPostmonVO.setBairro(jsonObject.getString("bairro"));
            wsCepPostmonVO.setCidade(jsonObject.getString("cidade"));
            wsCepPostmonVO.setCep(jsonObject.getString("cep"));
            wsCepPostmonVO.setLogradouro(jsonObject.getString("logradouro"));
            wsCepPostmonVO.setEstado_area(jsonObject.getJSONObject("estado_info").getString("area_km2"));
            wsCepPostmonVO.setEstado_codigo_ibge(jsonObject.getJSONObject("estado_info").getString("codigo_ibge"));
            wsCepPostmonVO.setEstado_nome(jsonObject.getJSONObject("estado_info").getString("nome"));

            wsCepPostmonVO.setCidade_area(jsonObject.getJSONObject("cidade_info").getString("area_km2"));
            wsCepPostmonVO.setCidade_codigo_ibge(jsonObject.getJSONObject("cidade_info").getString("codigo_ibge"));
            wsCepPostmonVO.setEstado_sigla(jsonObject.getString("estado"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return wsCepPostmonVO;
    }

    public TabEnderecoVO getTabEnderecoVO(int tipEnd, String cep) {
        if ((wsCepPostmonVO = getCepPostmonVO(cep)) == null)
            return null;
        TabEnderecoVO enderecoVO = null;
        try {
            enderecoVO = new TabEnderecoVO(tipEnd, 112);
            enderecoVO.setSisTipoEnderecoVO(new SisTipoEnderecoDAO().getSisTipoEnderecoVO(tipEnd));
            enderecoVO.setCep(wsCepPostmonVO.getCep());
            enderecoVO.setLogradouro(wsCepPostmonVO.getLogradouro());
            enderecoVO.setNumero("");
            enderecoVO.setComplemento("");
            enderecoVO.setBairro(wsCepPostmonVO.getBairro());
            enderecoVO.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(wsCepPostmonVO.getCidade() + "-" + wsCepPostmonVO.getEstado_codigo_ibge()));
            enderecoVO.setSisMunicipio_id(enderecoVO.getSisMunicipioVO().getId());
            enderecoVO.setPontoReferencia("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return enderecoVO;
    }
}
