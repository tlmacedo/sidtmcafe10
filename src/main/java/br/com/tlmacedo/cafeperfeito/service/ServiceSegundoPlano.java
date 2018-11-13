package br.com.tlmacedo.cafeperfeito.service;

import br.com.tlmacedo.cafeperfeito.interfaces.Constants;
import javafx.concurrent.Task;

import java.math.BigDecimal;
import java.net.URL;

public class ServiceSegundoPlano implements Constants {

    URL url;
    BigDecimal getSaldo;
    //    WsCnpjReceitaWsVO wsCnpjReceitaWsVO;
//    TabEmpresaVO tabEmpresaVO;
//    TabProdutoVO tabProdutoVO;
//    TabEnderecoVO tabEnderecoVO;
//    WsCepPostmonVO wsCepPostmonVO;
//    WsEanCosmosVO wsEanCosmosVO;

    int qtdTarefas = 1;

    public boolean tarefaAbreCadastro(Task voidTask, int qtdTarefas) {
        return new ServiceAlertMensagem("Aguarde carregando dados do sistema...", "",
                "ic_aguarde_sentado_orange_32dp.png")
                .getProgressBar(voidTask, true, false, qtdTarefas);
    }

//    public void tarefaAbreCadastroProduto(Task voidTask, int qtdTarefas) {
//        new ServiceAlertMensagem("Aguarde carregando dados do sistema...", "",
//                "ic_aguarde_sentado_orange_32dp.png")
//                .getProgressBar(voidTask, true, false, qtdTarefas);
//    }


}
