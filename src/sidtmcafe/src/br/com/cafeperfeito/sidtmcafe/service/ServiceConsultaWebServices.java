package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.model.dao.*;
import br.com.cafeperfeito.sidtmcafe.model.vo.*;
import javafx.concurrent.Task;
import javafx.util.Pair;

public class ServiceConsultaWebServices {

    public void getEnderecoCep_postmon(TabEnderecoVO endereco, String busca) {
        Task<Void> buscaCep = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage(String.format("Pesquisando C.E.P.: [%s]",busca));
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
                updateMessage(String.format("Pesquisando C.N.P.J: [%s]",busca));
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

    public String getProdutoNcmCest_WsEanCosmos(String busca) {
        final String[] retorno = new String[1];
        Task<Void> buscaCNPJ = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage(String.format("Pesquisando código de barra: [%s]", busca));
                Thread.sleep(300);
                retorno[0] = new WsEanCosmosDAO().getWsEanCosmosVO(busca);
                return null;
            }
        };
        new ServiceAlertMensagem("Aguarde pesquisando cnpj na receita federal...", "",
                "ic_aguarde_sentado_orange_32dp.png").getProgressBar(buscaCNPJ, true, false, 1);
        return retorno[0];
    }
}
