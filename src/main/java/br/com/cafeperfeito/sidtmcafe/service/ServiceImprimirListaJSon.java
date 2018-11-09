package br.com.cafeperfeito.sidtmcafe.service;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ServiceImprimirListaJSon {

    static ObjectMapper mapper = new ObjectMapper();

    public static void imprimirLista(List list) throws IOException {
        System.out.println("\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list) + "\n");
    }

}
