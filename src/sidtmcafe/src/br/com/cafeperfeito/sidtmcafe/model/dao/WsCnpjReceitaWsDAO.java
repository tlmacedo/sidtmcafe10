package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.*;
import com.jfoenix.controls.IFXTextInputControl;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema.*;

//import br.com.cafeperfeito.sidtmcafe.service.FormatarDado;

public class WsCnpjReceitaWsDAO extends BuscaWebService implements Constants {

    WsCnpjReceitaWsVO wsCnpjReceitaWsVO;

    JSONObject getRetWs(String busca) {
        return (jsonObject = getJsonObjectHttpUrlConnection(WS_RECEITAWS_URL + busca, WS_RECEITAWS_TOKEN, "/days/0"));
    }


    public void getWsCnpjReceitaWsVO(String busca) {
        if (getRetWs(busca) == null)
            return;
        try {
            wsCnpjReceitaWsVO = new WsCnpjReceitaWsVO();

            wsCnpjReceitaWsVO.setStatus(jsonObject.getString("status").toUpperCase());
            if (wsCnpjReceitaWsVO.getStatus().equals("ERROR")) {
                wsCnpjReceitaWsVO.setMessage(jsonObject.getString("message").toUpperCase());
                wsCnpjReceitaWsVO = null;
                return;
            }
            wsCnpjReceitaWsVO.setCnpj(jsonObject.getString("cnpj").toUpperCase());
            wsCnpjReceitaWsVO.setTipo(jsonObject.getString("tipo").toUpperCase());
            wsCnpjReceitaWsVO.setAbertura(jsonObject.getString("abertura") != null ? Date.valueOf(LocalDate.parse(jsonObject.getString("abertura").toUpperCase(), DTF_DATA)) : null);


            wsCnpjReceitaWsVO.setNome(jsonObject.getString("nome").toUpperCase());
            wsCnpjReceitaWsVO.setFantasia(jsonObject.getString("fantasia").equals("") ? "***" : jsonObject.getString("fantasia").toUpperCase());

            JSONArray listAtividadePrincipal = jsonObject.getJSONArray("atividade_principal");
            if (listAtividadePrincipal != null) {
                wsCnpjReceitaWsVO.setAtividadePrincipal(new ArrayList<>());
                for (int i = 0; i < listAtividadePrincipal.length(); i++)
                    wsCnpjReceitaWsVO.getAtividadePrincipal()
                            .add(new TabEmpresaReceitaFederalVO(0, 0, 1,
                                    listAtividadePrincipal.getJSONObject(i).getString("code").toUpperCase(),
                                    listAtividadePrincipal.getJSONObject(i).getString("text").toUpperCase()));
            }

            JSONArray listAtividadeSecundaria = jsonObject.getJSONArray("atividades_secundarias");
            if (listAtividadeSecundaria != null) {
                wsCnpjReceitaWsVO.setAtividadesSecundarias(new ArrayList<>());
                for (int i = 0; i < listAtividadeSecundaria.length(); i++)
                    wsCnpjReceitaWsVO.getAtividadesSecundarias()
                            .add(new TabEmpresaReceitaFederalVO(0, 0, 0,
                                    listAtividadeSecundaria.getJSONObject(i).getString("code").toUpperCase(),
                                    listAtividadeSecundaria.getJSONObject(i).getString("text").toUpperCase()));
            }

            JSONArray listQsa = jsonObject.getJSONArray("qsa");
            if (listQsa != null) {
                wsCnpjReceitaWsVO.setQsa(new ArrayList<>());
                for (int i = 0; i < listQsa.length(); i++) {
                    wsCnpjReceitaWsVO.getQsa()
                            .add(new TabEmpresaReceitaFederalVO(0, 0, 2,
                                    listQsa.getJSONObject(i).getString("qual").toUpperCase(),
                                    listQsa.getJSONObject(i).getString("nome").toUpperCase()));
                }
            }

            wsCnpjReceitaWsVO.setNaturezaJuridica(jsonObject.getString("natureza_juridica").toUpperCase());
            wsCnpjReceitaWsVO.setLogradouro(jsonObject.getString("logradouro").toUpperCase());
            wsCnpjReceitaWsVO.setNumero(jsonObject.getString("numero").toUpperCase());
            wsCnpjReceitaWsVO.setComplemento(jsonObject.getString("complemento").toUpperCase());
            wsCnpjReceitaWsVO.setCep(jsonObject.getString("cep").toUpperCase());
            wsCnpjReceitaWsVO.setBairro(jsonObject.getString("bairro").toUpperCase());
            wsCnpjReceitaWsVO.setMunicipio(jsonObject.getString("municipio").toUpperCase());
            if (wsCnpjReceitaWsVO.getMunicipio().equals("")) {
                wsCnpjReceitaWsVO.setSisMunicipioVO(new SisMunicipioVO());
                wsCnpjReceitaWsVO.setSisUfVO(new SisUfVO());
            } else {
                wsCnpjReceitaWsVO.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(wsCnpjReceitaWsVO.getMunicipio(), true));
                wsCnpjReceitaWsVO.setSisUfVO(wsCnpjReceitaWsVO.getSisMunicipioVO().getUfVO());
            }
            wsCnpjReceitaWsVO.setSisMunicipio_id(wsCnpjReceitaWsVO.getSisMunicipioVO().getId());
            wsCnpjReceitaWsVO.setUf(wsCnpjReceitaWsVO.getSisUfVO().getSigla());
            wsCnpjReceitaWsVO.setEmail(jsonObject.getString("email").toLowerCase());
            wsCnpjReceitaWsVO.setTelefone(jsonObject.getString("telefone").toUpperCase());
            wsCnpjReceitaWsVO.setEfr(jsonObject.getString("efr").toUpperCase());
            wsCnpjReceitaWsVO.setSituacao(jsonObject.getString("situacao").toUpperCase());
            if (jsonObject.getString("data_situacao") != null && !jsonObject.getString("data_situacao").isEmpty())
                wsCnpjReceitaWsVO.setDataSituacao(Date.valueOf(LocalDate.parse(jsonObject.getString("data_situacao"), DTF_DATA)));
            wsCnpjReceitaWsVO.setMotivoSituacao(jsonObject.getString("motivo_situacao").toUpperCase());
            wsCnpjReceitaWsVO.setSituacaoEspecial(jsonObject.getString("situacao_especial").toUpperCase());
            if (jsonObject.getString("data_situacao_especial") != null && !jsonObject.getString("data_situacao_especial").isEmpty())
                wsCnpjReceitaWsVO.setDataSituacaoEspecial(Date.valueOf(LocalDate.parse(jsonObject.getString("data_situacao_especial"), DTF_DATA)));
            wsCnpjReceitaWsVO.setCapitalSocial(jsonObject.getString("capital_social"));
            wsCnpjReceitaWsVO.setExtra("");//jsonObject.getString("extra").toUpperCase());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getTabEmpresaVO(TabEmpresaVO empresaVO, String busca) {
        getWsCnpjReceitaWsVO(busca);
        if (wsCnpjReceitaWsVO == null) {
            ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
            alertMensagem.setCabecalho("Retorno inválido!");
            alertMensagem.setPromptText(String.format("%s, o C.N.P.J: [%s] informado, não foi localizado na base de dados!",
                    USUARIO_LOGADO_APELIDO, busca));
            alertMensagem.setStrIco("ic_webservice_24dp");
            alertMensagem.getRetornoAlert_OK();
            return;
        }
        empresaVO.setCnpj(wsCnpjReceitaWsVO.getCnpj());
        empresaVO.setRazao(wsCnpjReceitaWsVO.getNome());
        empresaVO.setFantasia(wsCnpjReceitaWsVO.getFantasia());
        empresaVO.setDataAbertura(wsCnpjReceitaWsVO.getAbertura());
        empresaVO.setNaturezaJuridica(wsCnpjReceitaWsVO.getNaturezaJuridica());

        TabEnderecoVO endereco = new TabEnderecoVO(1, 0);
        if (wsCnpjReceitaWsVO.getSituacao().toLowerCase().equals("ativa")) {
            endereco.setId(empresaVO.getTabEnderecoVOList().get(0).getId());
            endereco.setCep(wsCnpjReceitaWsVO.getCep());
            endereco.setLogradouro(wsCnpjReceitaWsVO.getLogradouro());
            endereco.setNumero(wsCnpjReceitaWsVO.getNumero());
            endereco.setComplemento(wsCnpjReceitaWsVO.getComplemento());
            endereco.setBairro(wsCnpjReceitaWsVO.getBairro());
            endereco.setSisMunicipioVO(wsCnpjReceitaWsVO.getSisMunicipioVO());
            endereco.setSisMunicipio_id(wsCnpjReceitaWsVO.getSisMunicipio_id());
            endereco.setPontoReferencia("");
        } else {
            empresaVO.setSisSituacaoSistema_id(7);
            empresaVO.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(empresaVO.getSisSituacaoSistema_id()));
        }
        empresaVO.getTabEnderecoVOList().set(0, endereco);

        if (!wsCnpjReceitaWsVO.getEmail().equals("")) {
            List<String> emailList = ServiceValidarDado.getEmailsList(wsCnpjReceitaWsVO.getEmail());
            if (emailList != null)
                for (String mail : emailList) {
                    if (empresaVO.getTabEmailHomePageVOList().stream().noneMatch(e -> e.getDescricao().equals(mail)))
                        empresaVO.getTabEmailHomePageVOList().add(new TabEmailHomePageVO(mail, true));
                }

        }

        if (!wsCnpjReceitaWsVO.getTelefone().equals("")) {
            List<String> telefoneList = ServiceValidarDado.getTelefoneList(wsCnpjReceitaWsVO.getTelefone());
            if (telefoneList != null)
                for (String telefone : telefoneList)
                    if (empresaVO.getTabTelefoneVOList().stream().noneMatch(f -> f.getDescricao().contains(telefone))) {
                        empresaVO.getTabTelefoneVOList().add(new ServiceConsultaWebServices().getTelefone_WsPortabilidadeCelular(telefone));
                    }
        }
        empresaVO.getTabTelefoneVOList();
        empresaVO.getTabEmpresaReceitaFederalVOList().stream()
                .forEach(receita -> receita.setId(receita.getId() * (-1)));
        empresaVO.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getAtividadePrincipal());
        empresaVO.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getAtividadesSecundarias());
        empresaVO.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getQsa());
    }


}
