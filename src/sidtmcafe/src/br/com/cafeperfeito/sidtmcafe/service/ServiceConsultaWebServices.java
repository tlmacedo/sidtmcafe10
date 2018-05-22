package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.model.dao.WsCepPostmonDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.WsCnpjReceitaWsDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmpresaVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEnderecoVO;
import javafx.concurrent.Task;
import javafx.util.Pair;

public class ServiceConsultaWebServices {

    public TabEnderecoVO getEnderecoCep_postmon(Pair<Integer, String> buscaEnd) {
        final TabEnderecoVO[] enderecoVO = {null};
        Task<Void> buscaCep = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Pesquisando C.E.P. [" + ServiceFormatarDado.getValorFormatado(buscaEnd.getValue(), "cep") + "]");
                Thread.sleep(200);
                enderecoVO[0] = new WsCepPostmonDAO().getTabEnderecoVO(buscaEnd.getKey(), buscaEnd.getValue());
                return null;
            }
        };
        new ServiceAlertMensagem("Aguarde pesquisando cep nos correios...", "",
                "ic_aguarde_sentado_orange_32dp.png").getProgressBar(buscaCep, true, false, 1);
        return enderecoVO[0];
    }

    public TabEmpresaVO getSistuacaoCNPJ_receitaWs(TabEmpresaVO tabEmpresaVO, String busca) {
        final TabEmpresaVO[] empresaVO = {null};
        Task<Void> buscaCNPJ = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Pesquisando C.N.P.J [" + ServiceFormatarDado.getValorFormatado(busca, "cnpj") + "]");
                Thread.sleep(300);
                empresaVO[0] = new WsCnpjReceitaWsDAO().getTabEmpresaVO(tabEmpresaVO, busca);
                return null;
            }
        };
        new ServiceAlertMensagem("Aguarde pesquisando cnpj na receita federal...", "",
                "ic_aguarde_sentado_orange_32dp.png").getProgressBar(buscaCNPJ, true, false, 1);
        return empresaVO[0];
    }
}
