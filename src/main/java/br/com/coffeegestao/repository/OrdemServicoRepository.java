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
}