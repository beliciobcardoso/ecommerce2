package com.example.ecommerce.DAO.impl;

import com.example.ecommerce.DAO.ConfiguracaoJDBC;
import com.example.ecommerce.DAO.IDao;
import com.example.ecommerce.model.Produto;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
@Configuration
public class ProdutoDAOH2 implements IDao<Produto> {
    final static Logger logger = Logger.getLogger(ProdutoDAOH2.class);
    private final String jdbcDriver = "org.h2.Driver";
    private final String dbUrl = "jdbc:h2:d:\\h2\\db\\db_dados;INIT=RUNSCRIPT FROM 'create.sql'";
    private final String user = "sa";
    private final String password = "sa";
    private ConfiguracaoJDBC configuracaoJDBC;
    @Override
    public Produto salvar(Produto produto) throws SQLException {
        logger.info("Cadastrando o Produto: " + produto.getNomeProduto());
        configuracaoJDBC = new ConfiguracaoJDBC(jdbcDriver, dbUrl,user,password);
        Connection connection = configuracaoJDBC.getConnection();

        String query = String.format("INSERT INTO produto (nomeproduto, preco)" +
                " VALUES ('%S', '%S')", produto.getNomeProduto(), produto.getPreco());
        try{
            Statement statement = connection.createStatement();
            statement.execute(query,Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                produto.setId(resultSet.getInt(1));
                logger.info("Capturando o id do banco: " + resultSet.getInt(1));
            }
        }catch (Exception e){
            logger.info("Ocorreu um erro na aplicação!!!!!");
            e.printStackTrace();
        }finally {
            logger.info("Logout do banco efetuado com sucesso!!!!");
            connection.close();
        }
        return produto;
    }

    @Override
    public List<Produto> buscarTodos() throws SQLException {
        configuracaoJDBC = new ConfiguracaoJDBC(jdbcDriver,dbUrl,user,password);
        Connection connection = configuracaoJDBC.getConnection();

        String query = "SELECT * FROM produto;";
        List<Produto> produtos = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            while (result.next()){
                produtos.add(criarObjetoProduto(result));
            }
        }catch (Exception e){
            logger.info("Ocorreu um erro na aplicação!!!!!");
            e.printStackTrace();
        }finally {
            logger.info("Logout do banco efetuado com sucesso!!!!");
            connection.close();
        }
        return produtos;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {

        logger.info("Deletando o Produto de id: " + id);
        configuracaoJDBC = new ConfiguracaoJDBC(jdbcDriver, dbUrl, user, password);
        Connection connection = configuracaoJDBC.getConnection();
        String query = String.format("DELETE FROM produto WHERE id = '%S'", id);
        int resultquery = 0;
        boolean mensagem = false;
        try {
            Statement statement = connection.createStatement();

            resultquery = statement.executeUpdate(query);

            if (resultquery == 1) {
                //mensagem = "Deletado com sucesso!!!";
                mensagem = true;
                logger.info("Deletado com sucesso!!!");
            }else {
                logger.info("Erro ao deletar id " + id + " do banco");
            }
            logger.info("retorno do id do banco: " + resultquery);

        } catch (Exception e) {
            logger.info("Ocorreu um erro na aplicação!!!!!");
            e.printStackTrace();
        } finally {
            logger.info("Logout do banco efetuado com sucesso!!!!");
            connection.close();
        }
        return mensagem;
    }

    @Override
    public Produto buscarId(Integer id) throws SQLException {
        Produto produto = null;
        logger.info("Buscar o Produto pelo id: " + id);
        configuracaoJDBC = new ConfiguracaoJDBC(jdbcDriver, dbUrl,user,password);
        Connection connection = configuracaoJDBC.getConnection();

        String query = String.format("SELECT * FROM produto WHERE ID = %s",id);

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                produto = criarObjetoProduto(resultSet);
            }
        }catch (Exception e){
            logger.info("Ocorreu um erro na aplicação!!!!!");
            e.printStackTrace();
        }finally {
            logger.info("Logout do banco efetuado com sucesso!!!!");
            connection.close();
        }
        return produto;
    }

    @Override
    public Produto update(Produto produto) throws SQLException {
        logger.info("Alterar o Produto: " + produto.getNomeProduto());
        configuracaoJDBC = new ConfiguracaoJDBC(jdbcDriver, dbUrl,user,password);
        Connection connection = configuracaoJDBC.getConnection();

        String queryUpdate = String.format("UPDATE produto SET preco = '%S' WHERE id = '%S'",
                produto.getPreco(),produto.getId());

        int resultquery = 0;
        try {
            Statement statement = connection.createStatement();
            resultquery = statement.executeUpdate(queryUpdate);
            logger.info(resultquery);
            if (resultquery == 1){
                   produto = buscarId(produto.getId());
            }else {
                logger.info("Falhar ao altera o produto");
            }
        }catch (Exception e){
            logger.info("Ocorreu um erro na aplicação!!!!!");
            e.printStackTrace();
        }finally {
            logger.info("Logout do banco efetuado com sucesso!!!!");

            connection.close();
        }
        return produto;
    }

    private Produto criarObjetoProduto(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String nomeProduto = resultSet.getNString("nomeproduto");
        double preco = resultSet.getDouble("preco");
        String fornecedor = resultSet.getNString("fornecedor");
        return new Produto(id,nomeProduto,preco,fornecedor);
    }
}
