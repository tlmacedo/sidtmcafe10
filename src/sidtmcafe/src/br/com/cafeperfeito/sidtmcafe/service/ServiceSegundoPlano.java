package br.com.cafeperfeito.sidtmcafe.service;

import br.com.cafeperfeito.sidtmcafe.controller.ControllerCadastroProduto;
import br.com.cafeperfeito.sidtmcafe.interfaces.Constants;
import br.com.cafeperfeito.sidtmcafe.model.model.TabModel;
import javafx.concurrent.Task;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

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
    public void tarefaAbreCadastroEmpresa(Task voidTask, int qtdTarefas) {
        new ServiceAlertMensagem("Aguarde carregando dados do sistema...", "",
                "ic_aguarde_sentado_orange_32dp.png")
                .getProgressBar(voidTask, true, false, qtdTarefas);
    }

    public void tarefaAbreCadastroProduto(ControllerCadastroProduto cadastroProduto, List<Pair<String, String>> tarefa) {
        qtdTarefas = tarefa.size();
        Task<Void> voidTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("carregando.");
                for (Pair tarefaAtual : tarefa) {
                    updateProgress(tarefa.indexOf(tarefaAtual), qtdTarefas);
                    Thread.sleep(201);
                    updateMessage(tarefaAtual.getValue().toString());
                    switch (tarefaAtual.getKey().toString()) {
                        case "criarTabelaProduto":
                            TabModel.tabelaProduto();
                            break;
                        case "preencherCboUnidadeComercial":
                            cadastroProduto.preencherCboUnidadeComercial();
                            break;
                        case "preencherCboSituacaoSistema":
                            cadastroProduto.preencherCboSituacaoSistema();
                            break;
                        case "preencherCboFiscalOrigem":
                            cadastroProduto.preencherCboFiscalOrigem();
                            break;
                        case "preencherCboFiscalIcms":
                            cadastroProduto.preencherCboFiscalIcms();
                            break;
                        case "preencherCboFiscalPis":
                            cadastroProduto.preencherCboFiscalPis();
                            break;
                        case "preencherCboFiscalCofins":
                            cadastroProduto.preencherCboFiscalCofins();
                            break;
                        case "carregarListaProduto":
                            cadastroProduto.carregarListaProduto();
                            break;
                        case "preencherTabelaProduto":
                            //cadastroProduto.preencherTabelaProduto();
                            break;
                    }
                }
                updateProgress(qtdTarefas, qtdTarefas);
                return null;
            }
        };
        new ServiceAlertMensagem("Aguarde carregando dados do sistema...", "",
                "ic_aguarde_sentado_orange_32dp.png")
                .getProgressBar(voidTask, true, false, qtdTarefas);
    }


}
