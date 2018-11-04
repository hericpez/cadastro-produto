/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.betha.biblioteca.dao;

/**
 *
 * @author Heric
 */

import br.com.betha.biblioteca.model.Pessoa;
import br.com.betha.biblioteca.model.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDao {

    public Produto inserir(Produto produto) throws Exception {
        try {
            produto.setId(nextId());
            
            PreparedStatement stm = Connection.get().getPstm("INSERT INTO produtos (id, nome, comentario, altura, largura, profundidade) VALUES(?, ?, ?, ?, ?, ?)");
            stm.setLong(1, produto.getId());
            stm.setString(2, produto.getNome());
            stm.setString(3, produto.getComentario());
            stm.setString(4, produto.getAltura());
            stm.setString(5, produto.getLargura());
            stm.setString(6, produto.getProfundidade());
            stm.execute();
            
            return buscar(produto.getId());
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Erro ao inserir o registro", ex);
        }
    }

    public Produto atualizar(Produto produto) throws Exception {
        try {
            PreparedStatement stm = Connection.get().getPstm("UPDATE produtos SET nome = ?, comentario = ?, altura = ?, largura = ?, profundidade = ? WHERE id = ?");
            stm.setString(1, produto.getNome());
            stm.setString(2, produto.getComentario());
            stm.setString(3, produto.getAltura());
            stm.setString(4, produto.getLargura());
            stm.setString(5, produto.getProfundidade());
            stm.setLong(6, produto.getId());
            stm.execute();
            
            return buscar(produto.getId());
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Erro ao alterar o registro", ex);
        }
    }

    public void excluir(Produto produto) throws Exception {
        
        excluir(produto.getId());
    }

       public void excluir(Long id) throws Exception {
        try {
            PreparedStatement stm = Connection.get().getPstm("DELETE FROM produtos WHERE id = ?");
            stm.setLong(1, id);
            stm.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Erro ao excluir o registro", ex);
        }
    }

    public List<Produto> listarTodos() throws Exception {
        try {
            List<Produto> produtos = new ArrayList<>();
    
            Statement stm = Connection.get().getStm();
            ResultSet rs = stm.executeQuery("SELECT * FROM produtos");
            
            while(rs.next()) {
                produtos.add(parse(rs));
            }
            return produtos;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Erro ao excluir o registro", ex);
        }
    }

    public Produto buscar(Long id) throws Exception {
        try {
            PreparedStatement stm = Connection.get().getPstm("SELECT * FROM produtos WHERE id = ?");
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return parse(rs);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Erro ao buscar o registro", ex);
        }
    }

    private Long nextId() throws SQLException {
        ResultSet rs = Connection.get().getStm().executeQuery("SELECT NEXTVAL('produtos_seq')");
        rs.next();
        return rs.getLong(1);
    }

    private Produto parse(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setComentario(rs.getString("comentario"));
        p.setAltura(rs.getString("altura"));
        p.setLargura(rs.getString("largura"));
        p.setProfundidade(rs.getString("profundidade"));
        return p;
    }

}

