package br.com.coffeegestao.repository;

import br.com.coffeegestao.database.ConnectionFactory;
import br.com.coffeegestao.model.Aparelho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AparelhoRepository {

    public Aparelho salvar(Aparelho aparelho) {
        String sql = """
                INSERT INTO aparelhos (cliente_id, tipo, marca, modelo, numero_serie, observacoes)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, aparelho.getClienteId());
            stmt.setString(2, aparelho.getTipo());
            stmt.setString(3, aparelho.getMarca());
            stmt.setString(4, aparelho.getModelo());
            stmt.setString(5, aparelho.getNumeroSerie());
            stmt.setString(6, aparelho.getObservacoes());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aparelho.setId(generatedKeys.getInt(1));
                }
            }

            return aparelho;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar aparelho no banco de dados.", e);
        }
    }

    public Optional<Aparelho> buscarPorId(int id) {
        String sql = """
                SELECT id, cliente_id, tipo, marca, modelo, numero_serie, observacoes
                FROM aparelhos
                WHERE id = ?
                LIMIT 1;
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearAparelho(rs));
                }
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar aparelho por id.", e);
        }
    }

    public List<Aparelho> listarPorClienteId(int clienteId) {
        String sql = """
                SELECT id, cliente_id, tipo, marca, modelo, numero_serie, observacoes
                FROM aparelhos
                WHERE cliente_id = ?
                ORDER BY tipo;
                """;

        List<Aparelho> aparelhos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    aparelhos.add(mapearAparelho(rs));
                }
            }

            return aparelhos;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar aparelhos do cliente.", e);
        }
    }

    public List<Aparelho> listarTodos() {
        String sql = """
                SELECT id, cliente_id, tipo, marca, modelo, numero_serie, observacoes
                FROM aparelhos
                ORDER BY tipo;
                """;

        List<Aparelho> aparelhos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                aparelhos.add(mapearAparelho(rs));
            }

            return aparelhos;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar aparelhos.", e);
        }
    }

    public void atualizar(Aparelho aparelho) {
        String sql = """
                UPDATE aparelhos
                SET cliente_id = ?, tipo = ?, marca = ?, modelo = ?, numero_serie = ?, observacoes = ?
                WHERE id = ?;
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, aparelho.getClienteId());
            stmt.setString(2, aparelho.getTipo());
            stmt.setString(3, aparelho.getMarca());
            stmt.setString(4, aparelho.getModelo());
            stmt.setString(5, aparelho.getNumeroSerie());
            stmt.setString(6, aparelho.getObservacoes());
            stmt.setInt(7, aparelho.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar aparelho.", e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM aparelhos WHERE id = ?;";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar aparelho.", e);
        }
    }

    private Aparelho mapearAparelho(ResultSet rs) throws SQLException {
        return new Aparelho(
                rs.getInt("id"),
                rs.getInt("cliente_id"),
                rs.getString("tipo"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("numero_serie"),
                rs.getString("observacoes")
        );
    }
}