/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.betha.biblioteca.model;

import java.util.Map;

/**
 *
 * @author Heric
 */
public class Produto implements Parseable{
    
    private Long id;
    private String nome;
    private String comentario;
    private String altura;
    private String largura;
    private String profundidade;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getLargura() {
        return largura;
    }

    public void setLargura(String largura) {
        this.largura = largura;
    }

    public String getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(String profundidade) {
        this.profundidade = profundidade;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   
    @Override
    public String toString() {
        return String.format("{\"id\": %s, \"nome\": \"%s\", \"comentario\": \"%s\", \"altura\": \"%s\", \"largura\": \"%s\", \"profundidade\": \"%s\"}",id,nome,comentario,altura,largura,profundidade);
    }
  
    @Override
    public void parse(Map<String, Object> dados) {
        System.out.println("Parse Produto:"+dados.size());
        id=(dados.get("id") == null || dados.get("id").toString().isEmpty())?null:Long.parseLong(dados.get("id").toString());
        System.out.println("parse B");
        nome=(String)dados.get("nome");
       
        comentario=(String) dados.get("comentario");
        altura= (String) dados.get("altura");
        largura=(String) dados.get("largura");
        profundidade= (String) dados.get("profundidade");
        
    }
    
}
