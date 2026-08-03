package br.com.coffeegestao.repository;

import br.com.coffeegestao.database.ConnectionFactory;
import br.com.coffeegestao.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository {

    public Cliente salvar(Cliente cliente) {
        String sql = """
                INSERT INTO clientes (nome, telefone, cpf, email, endereco)
                VALUES (?, ?, ?, ?, ?);
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getEndereco());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1));
                }
            }

            return cliente;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente no banco de dados.", e);
        }
    }

    public Optional<Cliente> buscarPorId(int id) {
        String sql = """
                SELECT id, nome, telefone, cpf, email, endereco
                FROM clientes
                WHERE id = ?
                LIMIT 1;
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearCliente(rs));
                }
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente por id.", e);
        }
    }

    public List<Cliente> listarTodos() {
        String sql = """
                SELECT id, nome, telefone, cpf, email, endereco
                FROM clientes
                ORDER BY nome;
                """;

        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }

            return clientes;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar clientes.", e);
        }
    }

    public void atualizar(Cliente cliente) {
        String sql = """
                UPDATE clientes
                SET nome = ?, telefone = ?, cpf = ?, email = ?, endereco = ?
                WHERE id = ?;
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getEndereco());
            stmt.setInt(6, cliente.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cliente.", e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?;";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar cliente.", e);
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("cpf"),
                rs.getString("email"),
                rs.getString("endereco")
        );
    }
}