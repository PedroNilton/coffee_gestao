package br.com.coffeegestao.repository;

import br.com.coffeegestao.database.ConnectionFactory;
import br.com.coffeegestao.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository {

    public Produto salvar(Produto produto) {
        String sql = """
                INSERT INTO produtos (nome, descricao, preco, quantidade_estoque)
                VALUES (?, ?, ?, ?);
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidadeEstoque());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    produto.setId(generatedKeys.getInt(1));
                }
            }

            return produto;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto no banco de dados.", e);
        }
    }
}