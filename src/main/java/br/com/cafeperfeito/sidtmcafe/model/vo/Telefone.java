package br.com.cafeperfeito.sidtmcafe.model.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

//    private TelefoneOperadora telefoneOperadora;
//
//    private AssocEmpresaTelefone assocEmpresaTelefone;
//
//    private AssocColaboradorTelefone assocColaboradorTelefone;
//
//    private AssocContatoTelefone assocContatoTelefone;

}
