package com.example.ecommerce.DAO;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {
    public T salvar(T t) throws SQLException;
    public List<T> buscarTodos() throws SQLException;

    public boolean delete(Integer id) throws SQLException;

    public T buscarId(Integer id) throws SQLException;

    public T update(T t) throws SQLException;

}
