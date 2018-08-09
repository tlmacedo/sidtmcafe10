package br.com.cafeperfeito.sidtmcafe.teste;

import br.com.cafeperfeito.sidtmcafe.model.vo.SisTelefoneOperadoraVO;
import br.com.cafeperfeito.sidtmcafe.service.ServiceAlertMensagem;
import br.com.cafeperfeito.sidtmcafe.service.ServiceConsultaWebServices;
import br.com.cafeperfeito.sidtmcafe.service.ServiceFormatarDado;

import static br.com.cafeperfeito.sidtmcafe.service.ServiceVariavelSistema.USUARIO_LOGADO_APELIDO;

public class Testes {

    public static void main(String... args) {

        int tamMascara = 0;
        String tipMascara = "telefone8";
        String valor = "38776148";
        System.out.printf("mascara de retorno  tipMascara[%s]:\n    [%s]\n", tipMascara, ServiceFormatarDado.gerarMascara(tipMascara));
        System.out.printf("valor formatado  tipMascara[%s]  valor[%s]\n    [%s]\n", tipMascara, valor, ServiceFormatarDado.getValorFormatado(valor, tipMascara));
        tipMascara = "celular9";
        valor = "981686148";
        System.out.printf("mascara de retorno  tipMascara[%s]:\n    [%s]\n", tipMascara, ServiceFormatarDado.gerarMascara(tipMascara));
        System.out.printf("valor formatado  tipMascara[%s]  valor[%s]\n    [%s]\n", tipMascara, valor, ServiceFormatarDado.getValorFormatado(valor, tipMascara));


        //SisTelefoneOperadoraVO operadoraVO = new ServiceConsultaWebServices().getOperadoraTelefone_WsPortabilidadeCelular("92981686148");
//        if ((operadoraVO = new ServiceConsultaWebServices().getOperadoraTelefone_WsPortabilidadeCelular("92981686148")) == null) {
//            ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
//            alertMensagem.setCabecalho("Dado inválido!");
//            alertMensagem.setPromptText(String.format("%s, o cep: [%s] não foi localizado na base de dados!",
//                    USUARIO_LOGADO_APELIDO, txtEndCEP.getText()));
//            alertMensagem.setStrIco("ic_webservice_24dp");
//            alertMensagem.getRetornoAlert_OK();
//            txtEndCEP.requestFocus();
//        } else {
//            setEnderecoVO(enderecoBuscaCEP);
//            txtEndNumero.requestFocus();
//        }


    }


//    public static void main(String... args) {
//        System.out.println("cnpj: [" + new ServiceFormatarDado().gerarMascara("cnpj", 0, "#") + "]");
//        System.out.println("cpf: [" + new ServiceFormatarDado().gerarMascara("cpf", 0, "#") + "]");
//        System.out.println("barcode: [" + new ServiceFormatarDado().gerarMascara("barcode", 0, "#") + "]");
//        System.out.println("moeda: [" + new ServiceFormatarDado().gerarMascara("moeda3", 0, "#") + "]");
//        System.out.println("cep: [" + new ServiceFormatarDado().gerarMascara("cep", 0, "#") + "]");
//        System.out.println("ncm: [" + new ServiceFormatarDado().gerarMascara("ncm", 0, "#") + "]");
//        System.out.println("cest: [" + new ServiceFormatarDado().gerarMascara("cest", 0, "#") + "]");
//        System.out.println("nfechave: [" + new ServiceFormatarDado().gerarMascara("nfechave", 0, "#") + "]");
//        System.out.println("nfenumero: [" + new ServiceFormatarDado().gerarMascara("nfenumero", 0, "#") + "]");
//        System.out.println("nfedocorigem: [" + new ServiceFormatarDado().gerarMascara("nfedocorigem", 0, "#") + "]");
//        System.out.println("telefone8: [" + new ServiceFormatarDado().gerarMascara("telefone", 8, "#") + "]");
//        System.out.println("telefone9: [" + new ServiceFormatarDado().gerarMascara("telefone", 9, "#") + "]");
//        System.out.println("ie(ac): [" + new ServiceFormatarDado().gerarMascara("ieac", 0, "#") + "]");
//        System.out.println("ie(al): [" + new ServiceFormatarDado().gerarMascara("ieal", 0, "#") + "]");
//        System.out.println("ie(am): [" + new ServiceFormatarDado().gerarMascara("ieam", 0, "#") + "]");
//        System.out.println("ie(ap): [" + new ServiceFormatarDado().gerarMascara("ieap", 0, "#") + "]");
//        System.out.println("ie(ba): [" + new ServiceFormatarDado().gerarMascara("ieba", 0, "#") + "]");
//        System.out.println("ie(ce): [" + new ServiceFormatarDado().gerarMascara("iece", 0, "#") + "]");
//        System.out.println("ie(df): [" + new ServiceFormatarDado().gerarMascara("iedf", 0, "#") + "]");
//        System.out.println("ie(es): [" + new ServiceFormatarDado().gerarMascara("iees", 0, "#") + "]");
//        System.out.println("ie(go): [" + new ServiceFormatarDado().gerarMascara("iego", 0, "#") + "]");
//        System.out.println("ie(ma): [" + new ServiceFormatarDado().gerarMascara("iema", 0, "#") + "]");
//        System.out.println("ie(mg): [" + new ServiceFormatarDado().gerarMascara("iemg", 0, "#") + "]");
//        System.out.println("ie(ms): [" + new ServiceFormatarDado().gerarMascara("iems", 0, "#") + "]");
//        System.out.println("ie(mt): [" + new ServiceFormatarDado().gerarMascara("iemt", 0, "#") + "]");
//        System.out.println("ie(pa): [" + new ServiceFormatarDado().gerarMascara("iepa", 0, "#") + "]");
//        System.out.println("ie(pb): [" + new ServiceFormatarDado().gerarMascara("iepb", 0, "#") + "]");
//        System.out.println("ie(pe): [" + new ServiceFormatarDado().gerarMascara("iepe", 0, "#") + "]");
//        System.out.println("ie(pi): [" + new ServiceFormatarDado().gerarMascara("iepi", 0, "#") + "]");
//        System.out.println("ie(pr): [" + new ServiceFormatarDado().gerarMascara("iepr", 0, "#") + "]");
//        System.out.println("ie(rj): [" + new ServiceFormatarDado().gerarMascara("ierj", 0, "#") + "]");
//        System.out.println("ie(rn): [" + new ServiceFormatarDado().gerarMascara("iern", 0, "#") + "]");
//        System.out.println("ie(ro): [" + new ServiceFormatarDado().gerarMascara("iero", 0, "#") + "]");
//        System.out.println("ie(rr): [" + new ServiceFormatarDado().gerarMascara("ierr", 0, "#") + "]");
//        System.out.println("ie(rs): [" + new ServiceFormatarDado().gerarMascara("iers", 0, "#") + "]");
//        System.out.println("ie(sc): [" + new ServiceFormatarDado().gerarMascara("iesc", 0, "#") + "]");
//        System.out.println("ie(se): [" + new ServiceFormatarDado().gerarMascara("iese", 0, "#") + "]");
//        System.out.println("ie(sp): [" + new ServiceFormatarDado().gerarMascara("iesp", 0, "#") + "]");
//        System.out.println("ie(to): [" + new ServiceFormatarDado().gerarMascara("ieto", 0, "#") + "]");
//
//        System.out.println("cnpj(08009246000136): [" + new ServiceFormatarDado().getValorFormatado("08009246000136", "cnpj") + "]");
//        System.out.println("cpf(52309550230): [" + new ServiceFormatarDado().getValorFormatado("52309550230", "cpf") + "]");
//        System.out.println("barcode(7896423421255): [" + new ServiceFormatarDado().getValorFormatado("7896423421255", "barcode") + "]");
//        System.out.println("moeda(29902): [" + new ServiceFormatarDado().getValorFormatado("29902", "moeda3") + "]");
//        System.out.println("cep(69067360): [" + new ServiceFormatarDado().getValorFormatado("69067360", "cep") + "]");
//        System.out.println("ncm(09012100): [" + new ServiceFormatarDado().getValorFormatado("09012100", "ncm") + "]");
//        System.out.println("cest(1234567): [" + new ServiceFormatarDado().getValorFormatado("1234567", "cest") + "]");
//        System.out.println("nfechave(35180406981833000248550010000000471027609712): [" + new ServiceFormatarDado().getValorFormatado("35180406981833000248550010000000471027609712", "nfechave") + "]");
//        System.out.println("nfenumero(47): [" + new ServiceFormatarDado().getValorFormatado("47", "nfenumero")+"]");
//        System.out.println("nfedocorigem(041812441652): [" + new ServiceFormatarDado().getValorFormatado("041812441652","nfedocorigem") + "]");
//        System.out.println("telefone8(38776148): [" + new ServiceFormatarDado().getValorFormatado("38776148","telefone") + "]");
//        System.out.println("telefone9(981686148): [" + new ServiceFormatarDado().getValorFormatado("981686148","telefone") + "]");

//        System.out.println("ie(): [" + new ServiceFormatarDado().gerarMascara("ie", 0, "#") + "]");


//        System.out.print("digite um valor para conversão: ");
//        System.out.println("retorno: [" + getMoeda(new Scanner(System.in).nextLine(), 2) + "]");
//    }


}
