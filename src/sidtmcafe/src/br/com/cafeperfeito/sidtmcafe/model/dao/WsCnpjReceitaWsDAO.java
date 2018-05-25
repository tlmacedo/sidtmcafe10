package br.com.cafeperfeito.sidtmcafe.model.dao;

import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import javafx.collections.FXCollections;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if (empresaVO == null)
            empresaVO = new TabEmpresaVO(1);

        empresaVO.setCnpj(wsCnpjReceitaWsVO.getCnpj());
        empresaVO.setRazao(wsCnpjReceitaWsVO.getNome());
        empresaVO.setFantasia(wsCnpjReceitaWsVO.getFantasia());
        empresaVO.setDataAbertura(wsCnpjReceitaWsVO.getAbertura());
        empresaVO.setNaturezaJuridica(wsCnpjReceitaWsVO.getNaturezaJuridica());

        TabEnderecoVO enderecoVO;
        if (empresaVO.getTabEnderecoVOList() == null)
            empresaVO.setTabEnderecoVOList(FXCollections.observableArrayList(enderecoVO = new TabEnderecoVO(1)));
        else
            empresaVO.getTabEnderecoVOList().set(0, enderecoVO = new TabEnderecoVO(1));
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

        if (empresaVO.getTabEmailHomePageVOList() == null)
            empresaVO.setTabEmailHomePageVOList(new ArrayList<>());
        if (!wsCnpjReceitaWsVO.getEmail().equals("")) {
            Pattern p = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(wsCnpjReceitaWsVO.getEmail());
            String mail;
            while (m.find())
                if (empresaVO.getTabEmailHomePageVOList().stream().noneMatch(e -> e.getDescricao().equals(m.group())))
                    empresaVO.getTabEmailHomePageVOList().add(new TabEmailHomePageVO(m.group(), true));

        }

        if (empresaVO.getTabTelefoneVOList() == null)
            empresaVO.setTabTelefoneVOList(new ArrayList<>());
        if (!wsCnpjReceitaWsVO.getTelefone().equals("")) {
            Pattern p = Pattern.compile("\\d{4}-\\d{4}");
            Matcher m = p.matcher(wsCnpjReceitaWsVO.getTelefone());
            String fone;
            while (m.find())
                if (empresaVO.getTabTelefoneVOList().stream().noneMatch(f -> f.getDescricao().contains(m.group().replaceAll("\\D", "")))) {
                    if ((fone = m.group().replaceAll("\\D", "")).charAt(0) >= 8)
                        fone = "9" + fone;
                    empresaVO.getTabTelefoneVOList().add(new TabTelefoneVO(fone, new SisTelefoneOperadoraDAO().getSisTelefoneOperadoraVO(2)));
                }
        }

        empresaVO.setTabEmpresaReceitaFederalVOList(new ArrayList<>());

        empresaVO.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getAtividadePrincipal());
        empresaVO.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getAtividadesSecundarias());
        empresaVO.getTabEmpresaReceitaFederalVOList().addAll(wsCnpjReceitaWsVO.getQsa());

        return empresaVO;
    }


}
