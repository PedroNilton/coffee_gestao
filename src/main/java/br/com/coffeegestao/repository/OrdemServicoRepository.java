package br.com.coffeegestao.repository;

import br.com.coffeegestao.database.ConnectionFactory;
import br.com.coffeegestao.model.OrdemServico;
import br.com.coffeegestao.model.StatusOrdemServico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository {

    public OrdemServico salvar(OrdemServico ordem) {
        String sql = """
                INSERT INTO ordens_servico
                    (cliente_id, aparelho_id, defeito_relatado, diagnostico, solucao,
                     status, valor_servico, data_abertura, data_fechamento)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ordem.getClienteId());
            stmt.setInt(2, ordem.getAparelhoId());
            stmt.setString(3, ordem.getDefeitoRelatado());
            stmt.setString(4, ordem.getDiagnostico());
            stmt.setString(5, ordem.getSolucao());
            stmt.setString(6, ordem.getStatus().name());
            stmt.setDouble(7, ordem.getValorServico());
            stmt.setString(8, ordem.getDataAbertura().toString());
            stmt.setString(9, ordem.getDataFechamento() != null ? ordem.getDataFechamento().toString() : null);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ordem.setId(generatedKeys.getInt(1));
                }
            }

            return ordem;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar ordem de serviço no banco de dados.", e);
        }
    }
    public Optional<OrdemServico> buscarPorId(int id) {
        String sql = """
                SELECT id, cliente_id, aparelho_id, defeito_relatado, diagnostico, solucao,
                       status, valor_servico, data_abertura, data_fechamento
                FROM ordens_servico
                WHERE id = ?
                LIMIT 1;
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearOrdemServico(rs));
                }
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar ordem de serviço por id.", e);
        }
    }

    public List<OrdemServico> listarTodas() {
        String sql = """
                SELECT id, cliente_id, aparelho_id, defeito_relatado, diagnostico, solucao,
                       status, valor_servico, data_abertura, data_fechamento
                FROM ordens_servico
                ORDER BY data_abertura DESC;
                """;

        List<OrdemServico> ordens = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ordens.add(mapearOrdemServico(rs));
            }

            return ordens;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar ordens de serviço.", e);
        }
    }

    public List<OrdemServico> listarPorClienteId(int clienteId) {
        String sql = """
                SELECT id, cliente_id, aparelho_id, defeito_relatado, diagnostico, solucao,
                       status, valor_servico, data_abertura, data_fechamento
                FROM ordens_servico
                WHERE cliente_id = ?
                ORDER BY data_abertura DESC;
                """;

        List<OrdemServico> ordens = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ordens.add(mapearOrdemServico(rs));
                }
            }

            return ordens;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar ordens de serviço do cliente.", e);
        }
    }
}