package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEnderecoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.WsCepPostmonVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceAlertMensagem;
import br.com.cafeperfeito.sidtmcafe.service.ServiceBuscaWebService;
import org.json.JSONObject;

import static br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema.USUARIO_LOGADO_APELIDO;


public class WsCepPostmonDAO extends ServiceBuscaWebService implements Constants {

    JSONObject jsonObject;

    WsCepPostmonVO wsCepPostmonVO;

    public void getCepPostmonVO(String cep) {
        jsonObject = getJsonObjectWebService(WS_POSTMON_URL + cep);
        if (jsonObject == null)
            return;
        try {
            wsCepPostmonVO = new WsCepPostmonVO();
            wsCepPostmonVO.setBairro(jsonObject.getString("bairro").toUpperCase());
            wsCepPostmonVO.setCidade(jsonObject.getString("cidade").toUpperCase());
            wsCepPostmonVO.setCep(jsonObject.getString("cep").toUpperCase());
            wsCepPostmonVO.setLogradouro(jsonObject.getString("logradouro").toUpperCase());
            wsCepPostmonVO.setEstado_area(jsonObject.getJSONObject("estado_info").getString("area_km2").toUpperCase());
            wsCepPostmonVO.setEstado_codigo_ibge(jsonObject.getJSONObject("estado_info").getString("codigo_ibge").toUpperCase());
            wsCepPostmonVO.setEstado_nome(jsonObject.getJSONObject("estado_info").getString("nome").toUpperCase());

            wsCepPostmonVO.setCidade_area(jsonObject.getJSONObject("cidade_info").getString("area_km2").toUpperCase());
            wsCepPostmonVO.setCidade_codigo_ibge(jsonObject.getJSONObject("cidade_info").getString("codigo_ibge").toUpperCase());
            wsCepPostmonVO.setEstado_sigla(jsonObject.getString("estado").toUpperCase());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getTabEnderecoVO(TabEnderecoVO enderecoVO, String busca) {
        getCepPostmonVO(busca);
        if (wsCepPostmonVO == null) {
            ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho("Dado inválido!");
            alertMensagem.setPromptText(String.format("%s, o cep: [%s] não foi localizado na base de dados!",
                    USUARIO_LOGADO_APELIDO, busca));
            alertMensagem.setStrIco("ic_webservice_24dp");
            alertMensagem.getRetornoAlert_OK();
            return;
        }
        try {
            enderecoVO.setCep(wsCepPostmonVO.getCep());
            enderecoVO.setLogradouro(wsCepPostmonVO.getLogradouro());
            enderecoVO.setNumero("");
            enderecoVO.setComplemento("");
            enderecoVO.setBairro(wsCepPostmonVO.getBairro());
            enderecoVO.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(wsCepPostmonVO.getCidade() + "-" + wsCepPostmonVO.getEstado_codigo_ibge(), true));
            enderecoVO.setSisMunicipio_id(enderecoVO.getSisMunicipioVO().getId());
            enderecoVO.setPontoReferencia("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
