package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.model.dao.TabTelefoneDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.WsCepPostmonDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.WsCnpjReceitaWsDAO;
import br.com.cafeperfeito.sidtmcafe.model.dao.WsTelefoneOperadoraDAO;
import br.com.cafeperfeito.sidtmcafe.model.vo.SisTelefoneOperadoraVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEmpresaVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabEnderecoVO;
import br.com.cafeperfeito.sidtmcafe.model.vo.TabTelefoneVO;
import javafx.concurrent.Task;

public class ServiceConsultaWebServices {

    public TabEnderecoVO getEnderecoCep_postmon(int endereco_id, int sisTipoEndereco_id, String cep) {
        final TabEnderecoVO[] enderecoVO = {null};
        Task<Void> buscaCep = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Pesquisando C.E.P.: [" + ServiceFormatarDado.getValorFormatado(cep, "cep") + "]");
                Thread.sleep(200);
                enderecoVO[0] = new WsCepPostmonDAO().getTabEnderecoVO(endereco_id, sisTipoEndereco_id, cep);
                return null;
            }
        };
        new ServiceAlertMensagem("Aguarde pesquisando cep nos correios...", "",
                "ic_aguarde_sentado_orange_32dp.png").getProgressBar(buscaCep, true, false, 1);
        return enderecoVO[0];
    }

    public TabEmpresaVO getSistuacaoCNPJ_receitaWs(TabEmpresaVO empresa, String busca) {
        final TabEmpresaVO[] empresaVO = {null};
        Task<Void> buscaCNPJ = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Pesquisando C.N.P.J: [" + ServiceFormatarDado.getValorFormatado(busca, "cnpj") + "]");
                Thread.sleep(300);
                empresaVO[0] = new WsCnpjReceitaWsDAO().getTabEmpresaVO(empresa, busca);
                return null;
            }
        };
        new ServiceAlertMensagem("Aguarde pesquisando cnpj na receita federal...", "",
                "ic_aguarde_sentado_orange_32dp.png").getProgressBar(buscaCNPJ, true, false, 1);
        return empresaVO[0];
    }

    public TabTelefoneVO getTelefone_WsPortabilidadeCelular(String busca) {
        if (busca == null)
            return new TabTelefoneVO();
        return new TabTelefoneDAO().getTelefone_WsPortabilidadeCelular(busca);
    }
}
