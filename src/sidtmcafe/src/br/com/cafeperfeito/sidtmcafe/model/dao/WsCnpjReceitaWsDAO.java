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

            wsCnpjReceitaWsVO.setStatus(jsonObject.getString("status"));
            if (wsCnpjReceitaWsVO.getStatus().equals("ERROR")) {
                wsCnpjReceitaWsVO.setMessage(jsonObject.getString("message"));
                return wsCnpjReceitaWsVO = null;
            }
            wsCnpjReceitaWsVO.setCnpj(jsonObject.getString("cnpj"));
            wsCnpjReceitaWsVO.setTipo(jsonObject.getString("tipo"));
            if (jsonObject.getString("abertura") != null)
                wsCnpjReceitaWsVO.setAbertura(Date.valueOf(LocalDate.parse(jsonObject.getString("abertura"), DTF_DATA)));


            wsCnpjReceitaWsVO.setNome(jsonObject.getString("nome"));
            wsCnpjReceitaWsVO.setFantasia(jsonObject.getString("fantasia"));
            if (wsCnpjReceitaWsVO.getFantasia().equals("")) wsCnpjReceitaWsVO.setFantasia("***");

            JSONArray listAtividadePrincipal = jsonObject.getJSONArray("atividade_principal");
            if (listAtividadePrincipal != null) {
                wsCnpjReceitaWsVO.setAtividadePrincipal(new ArrayList<>());
                for (int i = 0; i < listAtividadePrincipal.length(); i++)
                    wsCnpjReceitaWsVO.getAtividadePrincipal()
                            .add(new TabEmpresaReceitaFederalVO(0, 0, 1,
                                    listAtividadePrincipal.getJSONObject(i).getString("code"),
                                    listAtividadePrincipal.getJSONObject(i).getString("text")));
            }

            JSONArray listAtividadeSecundaria = jsonObject.getJSONArray("atividades_secundarias");
            if (listAtividadeSecundaria != null) {
                wsCnpjReceitaWsVO.setAtividadesSecundarias(new ArrayList<>());
                for (int i = 0; i < listAtividadeSecundaria.length(); i++)
                    wsCnpjReceitaWsVO.getAtividadesSecundarias()
                            .add(new TabEmpresaReceitaFederalVO(0, 0, 0,
                                    listAtividadeSecundaria.getJSONObject(i).getString("code"),
                                    listAtividadeSecundaria.getJSONObject(i).getString("text")));
            }

            JSONArray listQsa = jsonObject.getJSONArray("qsa");
            if (listQsa != null) {
                wsCnpjReceitaWsVO.setQsa(new ArrayList<>());
                for (int i = 0; i < listQsa.length(); i++) {
                    wsCnpjReceitaWsVO.getQsa()
                            .add(new TabEmpresaReceitaFederalVO(0, 0, 2,
                                    listQsa.getJSONObject(i).getString("qual"),
                                    listQsa.getJSONObject(i).getString("nome")));
                }
            }

            wsCnpjReceitaWsVO.setNaturezaJuridica(jsonObject.getString("natureza_juridica"));
            wsCnpjReceitaWsVO.setLogradouro(jsonObject.getString("logradouro"));
            wsCnpjReceitaWsVO.setNumero(jsonObject.getString("numero"));
            wsCnpjReceitaWsVO.setComplemento(jsonObject.getString("complemento"));
            wsCnpjReceitaWsVO.setCep(jsonObject.getString("cep"));
            wsCnpjReceitaWsVO.setBairro(jsonObject.getString("bairro"));
            wsCnpjReceitaWsVO.setMunicipio(jsonObject.getString("municipio"));
            wsCnpjReceitaWsVO.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(wsCnpjReceitaWsVO.getMunicipio()));
            wsCnpjReceitaWsVO.setSisMunicipio_id(wsCnpjReceitaWsVO.getSisMunicipioVO().getId());
            wsCnpjReceitaWsVO.setSisUFVO(wsCnpjReceitaWsVO.getSisMunicipioVO().getUfVO());
            wsCnpjReceitaWsVO.setUf(wsCnpjReceitaWsVO.getSisUFVO().getSigla());
            wsCnpjReceitaWsVO.setEmail(jsonObject.getString("email"));
            wsCnpjReceitaWsVO.setTelefone(jsonObject.getString("telefone"));
            wsCnpjReceitaWsVO.setEfr(jsonObject.getString("efr"));
            wsCnpjReceitaWsVO.setSituacao(jsonObject.getString("situacao"));
            if (jsonObject.getString("data_situacao") != null && !jsonObject.getString("data_situacao").isEmpty())
                wsCnpjReceitaWsVO.setDataSituacao(Date.valueOf(LocalDate.parse(jsonObject.getString("data_situacao"), DTF_DATA)));
            wsCnpjReceitaWsVO.setMotivoSituacao(jsonObject.getString("motivo_situacao"));
            wsCnpjReceitaWsVO.setSituacaoEspecial(jsonObject.getString("situacao_especial"));
            if (jsonObject.getString("data_situacao_especial") != null && !jsonObject.getString("data_situacao_especial").isEmpty())
                wsCnpjReceitaWsVO.setDataSituacaoEspecial(Date.valueOf(LocalDate.parse(jsonObject.getString("data_situacao_especial"), DTF_DATA)));
            wsCnpjReceitaWsVO.setCapitalSocial(jsonObject.getString("capital_social"));
            wsCnpjReceitaWsVO.setExtra("");//jsonObject.getString("extra"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return wsCnpjReceitaWsVO;
    }

    public TabEmpresaVO getTabEmpresaVO(TabEmpresaVO empresaVO, String cnpj) {
        if (((wsCnpjReceitaWsVO = getWsCnpjReceitaWsVO(cnpj)) == null) || (wsCnpjReceitaWsVO.getStatus().equals("ERROR")))
            return empresaVO;

        TabEmpresaVO empresa = empresaVO;
        //if (empresaVO == null)
        //    empresaVO = new TabEmpresaVO(1);

        empresa.setCnpj(wsCnpjReceitaWsVO.getCnpj());
        empresa.setRazao(wsCnpjReceitaWsVO.getNome());
        empresa.setFantasia(wsCnpjReceitaWsVO.getFantasia());
        empresa.setDataAbertura(wsCnpjReceitaWsVO.getAbertura());
        empresa.setNaturezaJuridica(wsCnpjReceitaWsVO.getNaturezaJuridica());

        empresa.getTabEnderecoVOList().forEach(System.out::println);
        //if (empresaVO.getTabEnderecoVOList().get(0) == null)
        //    empresaVO.getTabEnderecoVOList().add(new TabEnderecoVO(1, 112));
        TabEnderecoVO enderecoVO = empresa.getTabEnderecoVOList().get(0);
        if (wsCnpjReceitaWsVO.getSituacao().toLowerCase().equals("ativa")) {
            enderecoVO.setCep(wsCnpjReceitaWsVO.getCep());
            enderecoVO.setLogradouro(wsCnpjReceitaWsVO.getLogradouro());
            enderecoVO.setNumero(wsCnpjReceitaWsVO.getNumero());
            enderecoVO.setComplemento(wsCnpjReceitaWsVO.getComplemento());
            enderecoVO.setBairro(wsCnpjReceitaWsVO.getBairro());
            enderecoVO.setSisMunicipioVO(wsCnpjReceitaWsVO.getSisMunicipioVO());
            enderecoVO.setSisMunicipio_id(wsCnpjReceitaWsVO.getSisMunicipio_id());
            enderecoVO.setPontoReferencia("");
        }
        empresa.getTabEnderecoVOList().set(0, enderecoVO);

        if (empresa.getTabEmailHomePageVOList() == null)
            empresa.setTabEmailHomePageVOList(new ArrayList<>());
        if (!wsCnpjReceitaWsVO.getEmail().equals("")) {
            List<String> emailList = ServiceValidarDado.getEmailsList(wsCnpjReceitaWsVO.getEmail());
            if (emailList != null)
                for (String mail : emailList) {
                    if (empresa.getTabEmailHomePageVOList().stream().noneMatch(e -> e.getDescricao().equals(mail)))
                        empresa.getTabEmailHomePageVOList().add(new TabEmailHomePageVO(mail, true));
                }

        }
        if (empresa.getTabTelefoneVOList() == null)
            empresa.setTabTelefoneVOList(new ArrayList<>());
        if (!wsCnpjReceitaWsVO.getTelefone().equals("")) {
            List<Pair<String, Integer>> telefoneList = ServiceValidarDado.getTelefoneList(wsCnpjReceitaWsVO.getTelefone());
            if (telefoneList != null)
                for (Pair<String, Integer> telefone : telefoneList)
                    if (empresa.getTabTelefoneVOList().stream().noneMatch(f -> f.getDescricao().contains(telefone.getKey())))
                        empresa.getTabTelefoneVOList().add(new TabTelefoneVO(telefone.getKey(), new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(2)));
        }
        empresa.setTabEmpresaReceitaFederalVOList(new ArrayList<>());

        empresa.getTabEmpresaReceitaFederalVOList().stream()
                .forEach(receita -> receita.setId(receita.getId() * (-1)));
        empresa.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getAtividadePrincipal());
        empresa.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getAtividadesSecundarias());
        empresa.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getQsa());

        return empresa;
    }


}
