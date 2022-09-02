package com.example.ecommerce.controller;

import com.example.ecommerce.model.Produto;
import com.example.ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService service;

    @PostMapping
    public Produto salvar(@RequestBody Produto produto) throws SQLException {
        return service.salvar(produto);
    }

    @GetMapping
    public List<Produto> buscarTodosProdutos() throws SQLException {
        return service.buscarTodosProdutos();
    }

    @PatchMapping
    public Produto update(@RequestBody Produto produto) throws SQLException {
        return service.update(produto);
    }

    @DeleteMapping
    public boolean delete(@RequestParam("id") Integer id) throws SQLException {
        return service.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Produto buscarId(@PathVariable Integer id) throws SQLException{
        return service.buscarID(id);
    }





    /*@RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public String buscarAll() {
        return "Buscar Produtos";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete() {
        return "Deletar Produto";
    }

    @RequestMapping(value = "/pedido", method = RequestMethod.GET)
    public String pedido(@RequestParam("id") int id) {
        return "Numero do Produto: " + id;
    }

    @RequestMapping(value = "/porcentagem", method = RequestMethod.GET)
    public String porcentagem(@RequestParam("num1") int num1, @RequestParam("num2") int num2) {
        int result = num1 * (num2 / 100);
        return "" + num1 + "% de " + num2 + " é: " + result;
    }

    @RequestMapping(value = "/porcentagem/{num1}", method = RequestMethod.GET)
    public String porcentagem(@PathVariable double num1) {
        double result = num1 * 3.14159265358979323846;
        return "" + num1 + " de  é: " + result;
    }*/


}
