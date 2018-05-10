package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
//import br.com.cafeperfeito.sidtmcafe.service.FormatarDado;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public TabEmpresaVO getTabEmpresaVO(String cnpj) {
        TabEmpresaVO tabEmpresaVO = null;
        jsonObject = getJsonObjectHttpUrlConnection(WS_RECEITAWS_URL + cnpj, WS_RECEITAWS_TOKEN, "/days/0");
        if (jsonObject == null)
            return tabEmpresaVO;
        try {
            if (jsonObject.getString("status").equals("ERROR")) {
                return tabEmpresaVO;
            }
            tabEmpresaVO = new TabEmpresaVO();

            tabEmpresaVO.setIsEmpresa(1);
            tabEmpresaVO.setCnpj(jsonObject.getString("cnpj"));
            tabEmpresaVO.setIe("");
            tabEmpresaVO.setRazao(jsonObject.getString("nome"));
            if (jsonObject.getString("fantasia").equals("")) {
                tabEmpresaVO.setFantasia("***");
            } else {
                tabEmpresaVO.setFantasia(jsonObject.getString("fantasia"));
            }
            tabEmpresaVO.setIsLoja(false);
            tabEmpresaVO.setIsCliente(true);
            tabEmpresaVO.setIsFornecedor(false);
            tabEmpresaVO.setIsTransportadora(false);
            tabEmpresaVO.setSisSituacaoSistemaVO(new SisSituacaoSistemaDAO().getSisSituacaoSistemaVO(1));
            tabEmpresaVO.setSisSituacaoSistema_id(tabEmpresaVO.getSisSituacaoSistemaVO().getId());
            if (jsonObject.getString("abertura") != null) {
                LocalDate ldAbertura = LocalDate.parse(jsonObject.getString("abertura"), DTF_DATA);
                tabEmpresaVO.setDataAbertura(Date.valueOf(ldAbertura));
            }
            tabEmpresaVO.setNaturezaJuridica(jsonObject.getString("natureza_juridica"));


            TabEnderecoVO tabEnderecoVO = new TabEnderecoVO(1);
            tabEmpresaVO.setId(0);
            if (jsonObject.getString("situacao").toLowerCase().equals("ativa")) {
                tabEnderecoVO.setCep(jsonObject.getString("cep"));
                tabEnderecoVO.setLogradouro(jsonObject.getString("logradouro"));
                tabEnderecoVO.setNumero(jsonObject.getString("numero"));
                tabEnderecoVO.setComplemento(jsonObject.getString("complemento"));
                tabEnderecoVO.setBairro(jsonObject.getString("bairro"));
                tabEnderecoVO.setSisMunicipioVO(new SisMunicipioDAO().getSisMunicipioVO(jsonObject.getString("municipio")));
                tabEnderecoVO.setSisMunicipio_id(tabEnderecoVO.getSisMunicipioVO().getId());
                tabEnderecoVO.setPontoReferencia("");
            }

            if (tabEmpresaVO.getTabEnderecoVOList() == null)
                tabEmpresaVO.setTabEnderecoVOList(new ArrayList<>());
            tabEmpresaVO.getTabEnderecoVOList().add(tabEnderecoVO);

            if (!jsonObject.getString("email").equals("")) {
                if (tabEmpresaVO.getTabEmailHomePageVOList() == null)
                    tabEmpresaVO.setTabEmailHomePageVOList(new ArrayList<>());
                List<String> emails = new ArrayList<>();
                for (int i = 0; i < (jsonObject.getString("email").length() - jsonObject.getString("email").replaceAll("@", "").length()); i++) {
                    emails.add(jsonObject.getString("email"));
                }
                for (String email : emails) {
                    TabEmailHomePageVO tabEmailHomePageVO = new TabEmailHomePageVO();
                    tabEmailHomePageVO.setId(0);
                    tabEmailHomePageVO.setIsEmail(true);
                    tabEmailHomePageVO.setDescricao(email);
                    tabEmpresaVO.getTabEmailHomePageVOList().add(tabEmailHomePageVO);
                }
            }

            if (!jsonObject.getString("telefone").equals("")) {
                if (tabEmpresaVO.getTabTelefoneVOList() == null)
                    tabEmpresaVO.setTabTelefoneVOList(new ArrayList<>());
                List<String> telefones = new ArrayList<>();
                for (String telefoneTemp : jsonObject.getString("telefone").replaceAll("[-]", "").split("/")) {
                    telefoneTemp = telefoneTemp.substring(telefoneTemp.length() - 9).replaceAll("[\\-/.() \\[\\]]", "");
                    if (telefoneTemp.length() == 8 && Integer.parseInt(telefoneTemp.substring(0, 1)) >= 8)
                        telefoneTemp = "9" + telefoneTemp;
                    telefones.add(telefoneTemp);
                }
                for (String telefone : telefones) {
                    TabTelefoneVO tabTelefoneVO = new TabTelefoneVO();
                    tabTelefoneVO.setId(0);
                    tabTelefoneVO.setDescricao(telefone);
                    tabTelefoneVO.setSisTelefoneOperadoraVO(new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(2));
                    tabTelefoneVO.setSisTelefoneOperadora_id(tabTelefoneVO.getSisTelefoneOperadoraVO().getId());
                    tabEmpresaVO.getTabTelefoneVOList().add(tabTelefoneVO);
                }

            }

            tabEmpresaVO.setTabEmpresaReceitaFederalVOList(getTabEmpresaReceitaFederalVO(jsonObject));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tabEmpresaVO;
    }


    List<TabEmpresaReceitaFederalVO> getTabEmpresaReceitaFederalVO(JSONObject object) {
        List<TabEmpresaReceitaFederalVO> receitaFederalVOList = new ArrayList<>();
        jsonArray = jsonObject.getJSONArray("atividade_principal");
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
