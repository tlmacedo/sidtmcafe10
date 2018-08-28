package br.com.cafeperfeito.sidtmcafe.teste;

public class Testes {

//    public static void main(String... args) {

//        new FiscalCestNcmDAO().getFiscalCestNcmVOList("0901").stream().forEach(System.out::println);
//
//        System.out.println("\n\n\n" +new FiscalCestNcmDAO().getFiscalCestNcmVO("0901"));

//        System.out.printf("diretorio.home: [%s]\n\n\n", PATH_IMAGE_DOWNLOAD);
//
//        System.out.printf("%s/%s.%s", PATH_IMAGE_DOWNLOAD, "codBarras", ".png");
//
//        //new ServiceConsultaWebServices().getSistuacaoCNPJ_receitaWs(new TabEmpresaVO(), "08009246000136");
//
//        new ServiceConsultaWebServices().getProdutoNcmCest_WsEanCosmos("7896078301063");
//
//
//        int tamMascara = 0;
//        String tipMascara = "6numero";
//        String valor = "123456789";
//        System.out.printf("mascara de retorno  tipMascara[%s]:\n    [%s]\n", tipMascara, ServiceFormatarDado.gerarMascara(tipMascara));
//        System.out.printf("valor formatado  tipMascara[%s]  valor[%s]\n    [%s]\n", tipMascara, valor, ServiceFormatarDado.getValorFormatado(valor, tipMascara));
//        tipMascara = "nfechave";
//        valor = "12345678901234567890123456789012345678901234";
//        System.out.printf("mascara de retorno  tipMascara[%s]:\n    [%s]\n", tipMascara, ServiceFormatarDado.gerarMascara(tipMascara));
//        System.out.printf("valor formatado  tipMascara[%s]  valor[%s]\n    [%s]\n", tipMascara, valor, ServiceFormatarDado.getValorFormatado(valor, tipMascara));


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


//    }


    public static void main(String... args) {
//        System.out.println("cnpj: [" + new ServiceFormatarDado().gerarMascara("cnpj") + "]");
//        System.out.println("cpf: [" + new ServiceFormatarDado().gerarMascara("cpf") + "]");
//        System.out.println("barcode: [" + new ServiceFormatarDado().gerarMascara("barcode") + "]");
//        System.out.println("6numero0(123456): [" + new ServiceFormatarDado().gerarMascara("6numero0") + "]");
//        System.out.println("6peso3(123456): [" + new ServiceFormatarDado().gerarMascara("6peso3") + "]");
//        System.out.println("8moeda2(123456): [" + new ServiceFormatarDado().gerarMascara("8moeda2") + "]");
//        System.out.println("8moeda3(123456): [" + new ServiceFormatarDado().gerarMascara("8moeda3") + "]");
//        System.out.println("cep(69067360): [" + new ServiceFormatarDado().gerarMascara("cep") + "]");
//        System.out.println("ncm(09012100): [" + new ServiceFormatarDado().gerarMascara("nfencm") + "]");
//        System.out.println("cest(1234567): [" + new ServiceFormatarDado().gerarMascara("nfecest") + "]");
//        System.out.println("nfechave: [" + new ServiceFormatarDado().gerarMascara("nfechave") + "]");
//        System.out.println("nfenumero: [" + new ServiceFormatarDado().gerarMascara("nfenumero") + "]");
//        System.out.println("nfedocorigem: [" + new ServiceFormatarDado().gerarMascara("nfedocorigem") + "]");
//        System.out.println("telefone8: [" + new ServiceFormatarDado().gerarMascara("telefone8") + "]");
//        System.out.println("telefone9: [" + new ServiceFormatarDado().gerarMascara("telefone9") + "]");
//        System.out.println("ie(ac): [" + new ServiceFormatarDado().gerarMascara("ieac") + "]");
//        System.out.println("ie(al): [" + new ServiceFormatarDado().gerarMascara("ieal") + "]");
//        System.out.println("ie(am): [" + new ServiceFormatarDado().gerarMascara("ieam") + "]");
//        System.out.println("ie(ap): [" + new ServiceFormatarDado().gerarMascara("ieap") + "]");
//        System.out.println("ie(ba): [" + new ServiceFormatarDado().gerarMascara("ieba") + "]");
//        System.out.println("ie(ce): [" + new ServiceFormatarDado().gerarMascara("iece") + "]");
//        System.out.println("ie(df): [" + new ServiceFormatarDado().gerarMascara("iedf") + "]");
//        System.out.println("ie(es): [" + new ServiceFormatarDado().gerarMascara("iees") + "]");
//        System.out.println("ie(go): [" + new ServiceFormatarDado().gerarMascara("iego") + "]");
//        System.out.println("ie(ma): [" + new ServiceFormatarDado().gerarMascara("iema") + "]");
//        System.out.println("ie(mg): [" + new ServiceFormatarDado().gerarMascara("iemg") + "]");
//        System.out.println("ie(ms): [" + new ServiceFormatarDado().gerarMascara("iems") + "]");
//        System.out.println("ie(mt): [" + new ServiceFormatarDado().gerarMascara("iemt") + "]");
//        System.out.println("ie(pa): [" + new ServiceFormatarDado().gerarMascara("iepa") + "]");
//        System.out.println("ie(pb): [" + new ServiceFormatarDado().gerarMascara("iepb") + "]");
//        System.out.println("ie(pe): [" + new ServiceFormatarDado().gerarMascara("iepe") + "]");
//        System.out.println("ie(pi): [" + new ServiceFormatarDado().gerarMascara("iepi") + "]");
//        System.out.println("ie(pr): [" + new ServiceFormatarDado().gerarMascara("iepr") + "]");
//        System.out.println("ie(rj): [" + new ServiceFormatarDado().gerarMascara("ierj") + "]");
//        System.out.println("ie(rn): [" + new ServiceFormatarDado().gerarMascara("iern") + "]");
//        System.out.println("ie(ro): [" + new ServiceFormatarDado().gerarMascara("iero") + "]");
//        System.out.println("ie(rr): [" + new ServiceFormatarDado().gerarMascara("ierr") + "]");
//        System.out.println("ie(rs): [" + new ServiceFormatarDado().gerarMascara("iers") + "]");
//        System.out.println("ie(sc): [" + new ServiceFormatarDado().gerarMascara("iesc") + "]");
//        System.out.println("ie(se): [" + new ServiceFormatarDado().gerarMascara("iese") + "]");
//        System.out.println("ie(sp): [" + new ServiceFormatarDado().gerarMascara("iesp") + "]");
//        System.out.println("ie(to): [" + new ServiceFormatarDado().gerarMascara("ieto") + "]");
//
//        System.out.println("cnpj(08009246000136): [" + new ServiceFormatarDado().getValorFormatado("08009246000136", "cnpj") + "]");
//        System.out.println("cpf(52309550230): [" + new ServiceFormatarDado().getValorFormatado("52309550230", "cpf") + "]");
//        System.out.println("barcode(7896423421255): [" + new ServiceFormatarDado().getValorFormatado("7896423421255", "barcode") + "]");
//        System.out.println("6numero0(123456): [" + new ServiceFormatarDado().getValorFormatado("123456","6numero0") + "]");
//        System.out.println("6peso3(123456): [" + new ServiceFormatarDado().getValorFormatado("123456","6peso3") + "]");
//        System.out.println("8moeda2(123456): [" + new ServiceFormatarDado().getValorFormatado("12345678","8moeda2") + "]");
//        System.out.println("8moeda3(123456): [" + new ServiceFormatarDado().getValorFormatado("12345678","8moeda3") + "]");
//        System.out.println("cep(69067360): [" + new ServiceFormatarDado().getValorFormatado("69067360","cep") + "]");
//        System.out.println("ncm(09012100): [" + new ServiceFormatarDado().getValorFormatado("09012100","nfencm") + "]");
//        System.out.println("cest(1234567): [" + new ServiceFormatarDado().getValorFormatado("1234567","nfecest") + "]");
//        System.out.println("nfechave(35180406981833000248550010000000471027609712): [" + new ServiceFormatarDado().getValorFormatado("35180406981833000248550010000000471027609712", "nfechave") + "]");
//        System.out.println("nfenumero(000000047): [" + new ServiceFormatarDado().getValorFormatado("000000047", "nfenumero") + "]");
//        System.out.println("nfedocorigem(041812441652): [" + new ServiceFormatarDado().getValorFormatado("041812441652", "nfedocorigem") + "]");
//        System.out.println("telefone8(38776148): [" + new ServiceFormatarDado().getValorFormatado("38776148", "telefone8") + "]");
//        System.out.println("telefone9(981686148): [" + new ServiceFormatarDado().getValorFormatado("981686148", "telefone9") + "]");
//
//        System.out.println("ie(AM): [" + new ServiceFormatarDado().gerarMascara("ieam") + "]");
//
//
//        System.out.print("digite um valor para conversão: ");
//
//
//


    }


}
