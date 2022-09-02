package com.example.ecommerce.service;

import com.example.ecommerce.DAO.IDao;
import com.example.ecommerce.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProdutoService {

    //Injeção de indepedencia
    @Autowired
    IDao<Produto> produtoIDao;

    public Produto salvar(Produto produto) throws SQLException {
        return produtoIDao.salvar(produto);
    }

    public List<Produto> buscarTodosProdutos() throws SQLException {
        return produtoIDao.buscarTodos();
    }

    public boolean delete(Integer id) throws SQLException {
       return produtoIDao.delete(id);
    }

    public Produto buscarID(Integer id) throws SQLException{
        return produtoIDao.buscarId(id);
    }

    public Produto update(Produto produto) throws SQLException{
        return produtoIDao.update(produto);
    }



}
