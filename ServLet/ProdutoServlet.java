/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.betha.biblioteca.servlet;

import br.com.betha.biblioteca.dao.Connection;
import br.com.betha.biblioteca.dao.EstoqueDao;
import br.com.betha.biblioteca.dao.FuncionarioDao;
import br.com.betha.biblioteca.dao.LivroDao;
import br.com.betha.biblioteca.dao.PessoaDao;
import br.com.betha.biblioteca.dao.ProdutoDao;
import br.com.betha.biblioteca.model.Estoque;
import br.com.betha.biblioteca.model.Funcionario;
import br.com.betha.biblioteca.model.Livro;
import br.com.betha.biblioteca.model.Pessoa;
import br.com.betha.biblioteca.model.Produto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Heric
 */

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet{
   
    private ProdutoDao produtoDao = new ProdutoDao();
   
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       System.out.println("ProdutoServlet: "+req.getParameter("id"));
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer=resp.getWriter();
        if(req.getParameter("id") != null && !req.getParameter("id").isEmpty()){
            try{
                Long id=Long.parseLong(req.getParameter("id"));
                Produto produto=produtoDao.buscar(id);
                writer.append(produto.toString());
            }catch(Exception ex){
                Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE,null,ex);
            }
        }else{
            try{
                List<Produto> produtos=produtoDao.listarTodos();
                List<String> produtosJSON= new ArrayList<>();
                for(Produto produto:produtos){
                    produtosJSON.add(produto.toString());
                }
                writer.append(produtosJSON.toString());
            }catch(Exception ex){
                Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Map<String,Object> dados= new HashMap<>();
        Produto produto=new Produto();
          for (String key : req.getParameterMap().keySet()) {
            dados.put(key, req.getParameter(key));
            System.out.println("Chave:"+key+" Dados:"+req.getParameter(key));
          }
        System.out.println("Dados Total:"+dados.size());
        produto.parse(dados);
        System.out.println("passou A.2");
        if(produto.getId() == null){
            System.out.println("passou B");
            try{
                produto=produtoDao.inserir(produto);
                
            }catch(Exception ex){
                   Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            
            try{
                Long id_funcionario=produto.getId();
                String nome=produto.getNome();
                String comentario=produto.getComentario();
                String altura=produto.getAltura();
                String largura=produto.getLargura();
                String profundidade=produto.getProfundidade();
               
                produto=produtoDao.atualizar(produto);
            }catch(Exception ex){
                   Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();
        writer.append(produto.toString());
        writer.flush();
        writer.close();
        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("id") != null && !req.getParameter("id").isEmpty()){
            try{
                //Produto produto=produtoDao.buscar(Long.parseLong(req.getParameter("id")));
                produtoDao.excluir(Long.parseLong(req.getParameter("id")));
                
            }catch(Exception ex){
                Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
