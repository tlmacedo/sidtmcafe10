package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.model.dao.*;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import javafx.concurrent.Task;

public class ServiceConsultaWebServices {

    public void getEnderecoCep_postmon(TabEnderecoVO endereco, String busca) {
        Task<Void> buscaCep = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Pesquisando C.E.P.: [" + ServiceFormatarDado.getValorFormatado(busca, "cep") + "]");
                Thread.sleep(200);
                new WsCepPostmonDAO().getTabEnderecoVO(endereco, busca);
                return null;
            }
        };
        new ServiceAlertMensagem("Aguarde pesquisando cep nos correios...", "",
                "ic_aguarde_sentado_orange_32dp.png").getProgressBar(buscaCep, true, false, 1);
        return;
    }

    public void getSistuacaoCNPJ_receitaWs(TabEmpresaVO empresa, String busca) {
        Task<Void> buscaCNPJ = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Pesquisando C.N.P.J: [" + ServiceFormatarDado.getValorFormatado(busca, "cnpj") + "]");
                Thread.sleep(300);
                new WsCnpjReceitaWsDAO().getTabEmpresaVO(empresa, busca);
                return null;
            }
        };
        new ServiceAlertMensagem("Aguarde pesquisando cnpj na receita federal...", "",
                "ic_aguarde_sentado_orange_32dp.png").getProgressBar(buscaCNPJ, true, false, 1);
        return;
    }

    public TabTelefoneVO getTelefone_WsPortabilidadeCelular(String busca) {
        return new TabTelefoneDAO().getTelefone_WsPortabilidadeCelular(busca);
    }

    public String getProdutoNcmCest_WsEanCosmos(TabProdutoVO produto, String busca) {
        final String[] strNcm = new String[1];
        Task<Void> buscaCNPJ = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Pesquisando código de barra: [" + ServiceFormatarDado.getValorFormatado(busca, "codebar") + "]");
                Thread.sleep(300);
                strNcm[0] = new WsEanCosmosDAO().getStringNcmProduto_EanCosmosVO(produto, busca);
                return null;
            }
        };
        new ServiceAlertMensagem("Aguarde pesquisando cnpj na receita federal...", "",
                "ic_aguarde_sentado_orange_32dp.png").getProgressBar(buscaCNPJ, true, false, 1);
        return strNcm[0];
    }
}
