package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import javafx.collections.FXCollections;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//import br.com.cafeperfeito.sidtmcafe.service.FormatarDado;

public class WsCnpjReceitaWsDAO extends BuscaWebService implements Constants {

    JSONObject jsonObject;
    JSONArray jsonArray;

    WsCnpjReceitaWsVO tabEmpresaVO;

    public WsCnpjReceitaWsVO getWsCnpjReceitaWsVO(String cnpj) {
        jsonObject = getJsonObjectHttpUrlConnection(WS_RECEITAWS_URL + cnpj, WS_RECEITAWS_TOKEN, "/days/0");
        if (jsonObject == null)
            return tabEmpresaVO = null;
        try {
            tabEmpresaVO = new WsCnpjReceitaWsVO();

            tabEmpresaVO.setStatus(jsonObject.getString("status"));
            if (tabEmpresaVO.getStatus().equals("ERROR")) {
                tabEmpresaVO.setMessage(jsonObject.getString("message"));
                return tabEmpresaVO = null;
            }
            tabEmpresaVO.setCnpj(jsonObject.getString("cnpj"));
            tabEmpresaVO.setTipo(jsonObject.getString("tipo"));
            tabEmpresaVO.setAbertura(jsonObject.getString("abertura"));
            tabEmpresaVO.setNome(jsonObject.getString("nome"));
            if (jsonObject.getString("fantasia").equals("")) tabEmpresaVO.setFantasia("***");
            else tabEmpresaVO.setFantasia(jsonObject.getString("fantasia"));

            jsonArray = jsonObject.getJSONArray("atividade_principal");
            List<Pair<String, String>> listAtividadePrincipal = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                listAtividadePrincipal.add(new Pair<String, String>(jsonArray.getJSONObject(i).getString("code"),
                        jsonArray.getJSONObject(i).getString("text")));
            }
            tabEmpresaVO.setAtividadePrincipal(listAtividadePrincipal);

            jsonArray = jsonObject.getJSONArray("atividades_secundarias");
            List<Pair<String, String>> listAtividadesSecundarias = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                listAtividadesSecundarias.add(new Pair<String, String>(jsonArray.getJSONObject(i).getString("code"),
                        jsonArray.getJSONObject(i).getString("text")));
            }
            tabEmpresaVO.setAtividadesSecundarias(listAtividadesSecundarias);

            jsonArray = jsonObject.getJSONArray("qsa");
            List<Pair<String, String>> listQsa = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                listQsa.add(new Pair<String, String>(jsonArray.getJSONObject(i).getString("qual"),
                        jsonArray.getJSONObject(i).getString("nome")));
            }
            tabEmpresaVO.setQsa(listQsa);

            tabEmpresaVO.setNaturezaJuridica(jsonObject.getString("natureza_juridica"));
            tabEmpresaVO.setLogradouro(jsonObject.getString("logradouro"));
            tabEmpresaVO.setNumero(jsonObject.getString("numero"));
            tabEmpresaVO.setComplemento(jsonObject.getString("complemento"));
            tabEmpresaVO.setCep(jsonObject.getString("cep"));
            tabEmpresaVO.setBairro(jsonObject.getString("bairro"));
            tabEmpresaVO.setMunicipio(jsonObject.getString("municipio"));
            tabEmpresaVO.setUf(jsonObject.getString("uf"));
            tabEmpresaVO.setEmail(jsonObject.getString("email"));
            tabEmpresaVO.setTelefone(jsonObject.getString("telefone"));
            tabEmpresaVO.setEfr(jsonObject.getString("efr"));
            tabEmpresaVO.setSituacao(jsonObject.getString("situacao"));
            tabEmpresaVO.setDataSituacao(jsonObject.getString("data_situacao"));
            tabEmpresaVO.setMotivoSituacao(jsonObject.getString("motivo_situacao"));
            tabEmpresaVO.setSituacaoEspecial(jsonObject.getString("situacao_especial"));
            tabEmpresaVO.setDataSituacaoEspecial(jsonObject.getString("data_situacao_especial"));
            tabEmpresaVO.setCapitalSocial(jsonObject.getString("capital_social"));
            tabEmpresaVO.setExtra("");//jsonObject.getString("extra"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tabEmpresaVO;
    }

    public TabEmpresaVO getTabEmpresaVO(TabEmpresaVO empresaVO, String cnpj) {
        jsonObject = getJsonObjectHttpUrlConnection(WS_RECEITAWS_URL + cnpj, WS_RECEITAWS_TOKEN, "/days/0");
        if (jsonObject == null)
            return empresaVO;

        if (jsonObject.getString("status").equals("ERROR"))
            return empresaVO;

        if (empresaVO == null) {
            empresaVO = new TabEmpresaVO(1);
        }
        empresaVO.setCnpj(jsonObject.getString("cnpj"));
        empresaVO.setRazao(jsonObject.getString("nome"));
        empresaVO.setFantasia(jsonObject.getString("fantasia"));
        if (empresaVO.getFantasia().equals("")) empresaVO.setFantasia("***");
        if (jsonObject.getString("abertura") != null)
            empresaVO.setDataAbertura(Date.valueOf(LocalDate.parse(jsonObject.getString("abertura"), DTF_DATA)));
        empresaVO.setNaturezaJuridica(jsonObject.getString("natureza_juridica"));

        TabEnderecoVO enderecoVO;
        if (empresaVO.getTabEnderecoVOList() == null)
            empresaVO.setTabEnderecoVOList(FXCollections.observableArrayList(enderecoVO = new TabEnderecoVO(1)));
        else
            empresaVO.getTabEnderecoVOList().set(0, enderecoVO = new TabEnderecoVO(1));
        if (jsonObject.getString("situacao").toLowerCase().equals("ativa")) {
            enderecoVO.setCep(jsonObject.getString("cep"));
            enderecoVO.setLogradouro(jsonObject.getString("logradouro"));
            enderecoVO.setNumero(jsonObject.getString("numero"));
            enderecoVO.setComplemento(jsonObject.getString("complemento"));
            enderecoVO.setBairro(jsonObject.getString("bairro"));
            enderecoVO.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(jsonObject.getString("municipio")));
            enderecoVO.setSisMunicipio_id(enderecoVO.getSisMunicipioVO().getId());
            enderecoVO.setPontoReferencia("");
        }

        String wsEmail = jsonObject.getString("email");
        if (empresaVO.getTabEmailHomePageVOList() == null)
            empresaVO.setTabEmailHomePageVOList(new ArrayList<>());
        if (!wsEmail.equals(""))
            if (empresaVO.getTabEmailHomePageVOList().stream().noneMatch(e -> e.getDescricao().equals(wsEmail)))
                empresaVO.getTabEmailHomePageVOList().add(new TabEmailHomePageVO(wsEmail, true));

        String wsTelefone = jsonObject.getString("telefone");
        if (empresaVO.getTabTelefoneVOList() == null)
            empresaVO.setTabTelefoneVOList(new ArrayList<>());
        if (!wsTelefone.equals("")) {
            Pattern p = Pattern.compile("\\d{4}-\\d{4}");
            Matcher m = p.matcher(wsTelefone);
            String fone;
            while (m.find())
                if (empresaVO.getTabTelefoneVOList().stream().noneMatch(f -> f.getDescricao().contains(m.group().replaceAll("\\D", "")))) {
                    if ((fone = m.group().replaceAll("\\D", "")).charAt(0) >= 8)
                        fone = "9" + fone;
                    empresaVO.getTabTelefoneVOList().add(new TabTelefoneVO(fone));
                }
        }

        empresaVO.setTabEmpresaReceitaFederalVOList(getTabEmpresaReceitaFederalVO(jsonObject));

        return empresaVO;
    }


    List<TabEmpresaReceitaFederalVO> getTabEmpresaReceitaFederalVO(JSONObject object) {
        List<TabEmpresaReceitaFederalVO> receitaFederalVOList = new ArrayList<>();
        jsonArray = jsonObject.getJSONArray("atividade_principal");
        for (String atividade : jsonArray.getString())

        for (int i = 0; i < jsonArray.length(); i++)
            receitaFederalVOList.add(new TabEmpresaReceitaFederalVO(0, 0, 1, jsonArray.getJSONObject(i).getString("code"),
                    jsonArray.getJSONObject(i).getString("text")));


        jsonArray = jsonObject.getJSONArray("atividades_secundarias");
        for (int i = 0; i < jsonArray.length(); i++)
            receitaFederalVOList.add(new TabEmpresaReceitaFederalVO(0, 0, 2, jsonArray.getJSONObject(i).getString("code"),
                    jsonArray.getJSONObject(i).getString("text")));

        jsonArray = jsonObject.getJSONArray("qsa");
        for (int i = 0; i < jsonArray.length(); i++)
            receitaFederalVOList.add(new TabEmpresaReceitaFederalVO(0, 0, 0, jsonArray.getJSONObject(i).getString("qual"),
                    jsonArray.getJSONObject(i).getString("nome")));

        receitaFederalVOList.add(new TabEmpresaReceitaFederalVO(0, 0, 0, "situacao",
                jsonObject.getString("situacao")));

        receitaFederalVOList.add(new TabEmpresaReceitaFederalVO(0, 0, 0, "data_situacao",
                jsonObject.getString("data_situacao")));

        receitaFederalVOList.add(new TabEmpresaReceitaFederalVO(0, 0, 0, "motivo_situacao",
                jsonObject.getString("motivo_situacao")));

        receitaFederalVOList.add(new TabEmpresaReceitaFederalVO(0, 0, 0, "situacao_especial",
                jsonObject.getString("situacao_especial")));

        receitaFederalVOList.add(new TabEmpresaReceitaFederalVO(0, 0, 0, "data_situacao_especial",
                jsonObject.getString("data_situacao_especial")));

        receitaFederalVOList.add(new TabEmpresaReceitaFederalVO(0, 0, 0, "capital_social", "R$ " +
                ServiceFormatarDado.getValueMoeda(jsonObject.getString("capital_social"), 2)));

        return receitaFederalVOList;
    }

}
