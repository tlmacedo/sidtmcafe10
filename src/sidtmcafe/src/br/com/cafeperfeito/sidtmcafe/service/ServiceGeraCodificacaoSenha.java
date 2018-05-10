package br.com.cafeperfeito.sidtmcafe.service;

import javafx.util.Pair;

public class ServiceGeraCodificacaoSenha {
    //    public static void main(String[] args) {
//        String myPassword = "4879";
//
//        String salt = ServiceValidaSenha.getSalt(20);
//
//        String mySecurePassword = ServiceValidaSenha.generateSecurePassword(myPassword, salt);
//
//        System.out.println("My secure password = " + mySecurePassword);
//        System.out.println("Salt value = " + salt);
//    }
    String senhaSalt, senhaCodificada;

    public String getSenhaSalt() {
        return senhaSalt;
    }

    public void setSenhaSalt(String senhaSalt) {
        this.senhaSalt = senhaSalt;
    }

    public String getSenhaCodificada() {
        return senhaCodificada;
    }

    public void setSenhaCodificada(String senhaCodificada) {
        this.senhaCodificada = senhaCodificada;
    }

    public void gerarSenhaCodificada(String senha) {
        setSenhaSalt(ServiceValidaSenha.getSalt(20));
        setSenhaCodificada(ServiceValidaSenha.generateSecurePassword(senha, getSenhaSalt()));
    }
}




