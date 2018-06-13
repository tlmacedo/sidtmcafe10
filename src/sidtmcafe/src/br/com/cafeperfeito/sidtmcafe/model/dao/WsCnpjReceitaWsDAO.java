package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.ServiceValidarDado;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//import br.com.cafeperfeito.sidtmcafe.service.FormatarDado;

public class WsCnpjReceitaWsDAO extends BuscaWebService implements Constants {

    WsCnpjReceitaWsVO wsCnpjReceitaWsVO;

    public WsCnpjReceitaWsVO getWsCnpjReceitaWsVO(String cnpj) {
        JSONObject jsonObject = getJsonObjectHttpUrlConnection(WS_RECEITAWS_URL + cnpj, WS_RECEITAWS_TOKEN, "/days/0");
        if (jsonObject == null)
            return wsCnpjReceitaWsVO = null;
        try {
            wsCnpjReceitaWsVO = new WsCnpjReceitaWsVO();

            wsCnpjReceitaWsVO.setStatus(jsonObject.getString("status").toUpperCase());
            if (wsCnpjReceitaWsVO.getStatus().equals("ERROR")) {
                wsCnpjReceitaWsVO.setMessage(jsonObject.getString("message").toUpperCase());
                return wsCnpjReceitaWsVO = null;
            }
            wsCnpjReceitaWsVO.setCnpj(jsonObject.getString("cnpj").toUpperCase());
            wsCnpjReceitaWsVO.setTipo(jsonObject.getString("tipo").toUpperCase());
            if (jsonObject.getString("abertura") != null)
                wsCnpjReceitaWsVO.setAbertura(Date.valueOf(LocalDate.parse(jsonObject.getString("abertura").toUpperCase(), DTF_DATA)));


            wsCnpjReceitaWsVO.setNome(jsonObject.getString("nome").toUpperCase());
            wsCnpjReceitaWsVO.setFantasia(jsonObject.getString("fantasia").toUpperCase());
            if (wsCnpjReceitaWsVO.getFantasia().equals("")) wsCnpjReceitaWsVO.setFantasia("***");

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
            wsCnpjReceitaWsVO.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(wsCnpjReceitaWsVO.getMunicipio()));
            wsCnpjReceitaWsVO.setSisMunicipio_id(wsCnpjReceitaWsVO.getSisMunicipioVO().getId());
            wsCnpjReceitaWsVO.setSisUFVO(wsCnpjReceitaWsVO.getSisMunicipioVO().getUfVO());
            wsCnpjReceitaWsVO.setUf(wsCnpjReceitaWsVO.getSisUFVO().getSigla());
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
        return wsCnpjReceitaWsVO;
    }

    public TabEmpresaVO getTabEmpresaVO(TabEmpresaVO empresaVO, String cnpj) {
        if (((wsCnpjReceitaWsVO = getWsCnpjReceitaWsVO(cnpj)) == null) || (wsCnpjReceitaWsVO.getStatus().equals("ERROR")))
            return empresaVO;
        TabEmpresaVO empresa = empresaVO;

        empresa.setCnpj(wsCnpjReceitaWsVO.getCnpj());
        empresa.setRazao(wsCnpjReceitaWsVO.getNome());
        empresa.setFantasia(wsCnpjReceitaWsVO.getFantasia());
        empresa.setDataAbertura(wsCnpjReceitaWsVO.getAbertura());
        empresa.setNaturezaJuridica(wsCnpjReceitaWsVO.getNaturezaJuridica());

        if (wsCnpjReceitaWsVO.getSituacao().toLowerCase().equals("ativa")) {
            TabEnderecoVO endereco = new TabEnderecoVO(1, 0);
            endereco.setCep(wsCnpjReceitaWsVO.getCep());
            endereco.setLogradouro(wsCnpjReceitaWsVO.getLogradouro());
            endereco.setNumero(wsCnpjReceitaWsVO.getNumero());
            endereco.setComplemento(wsCnpjReceitaWsVO.getComplemento());
            endereco.setBairro(wsCnpjReceitaWsVO.getBairro());
            endereco.setSisMunicipioVO(wsCnpjReceitaWsVO.getSisMunicipioVO());
            endereco.setSisMunicipio_id(wsCnpjReceitaWsVO.getSisMunicipio_id());
            endereco.setPontoReferencia("");
            empresa.getTabEnderecoVOList().set(0, endereco);
        }

        if (!wsCnpjReceitaWsVO.getEmail().equals("")) {
            List<String> emailList = ServiceValidarDado.getEmailsList(wsCnpjReceitaWsVO.getEmail());
            if (emailList != null)
                for (String mail : emailList) {
                    if (empresa.getTabEmailHomePageVOList().stream().noneMatch(e -> e.getDescricao().equals(mail)))
                        empresa.getTabEmailHomePageVOList().add(new TabEmailHomePageVO(mail, true));
                }

        }

        if (!wsCnpjReceitaWsVO.getTelefone().equals("")) {
            List<Pair<String, Integer>> telefoneList = ServiceValidarDado.getTelefoneList(wsCnpjReceitaWsVO.getTelefone());
            if (telefoneList != null)
                for (Pair<String, Integer> telefone : telefoneList)
                    if (empresa.getTabTelefoneVOList().stream().noneMatch(f -> f.getDescricao().contains(telefone.getKey())))
                        empresa.getTabTelefoneVOList().add(new TabTelefoneVO(telefone.getKey(), new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(2)));
        }

        empresa.getTabEmpresaReceitaFederalVOList().stream()
                .forEach(receita -> receita.setId(receita.getId() * (-1)));
        empresa.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getAtividadePrincipal());
        empresa.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getAtividadesSecundarias());
        empresa.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getQsa());

        return empresa;
    }


}
