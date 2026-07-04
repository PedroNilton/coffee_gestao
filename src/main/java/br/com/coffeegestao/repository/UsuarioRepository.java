package br.com.coffeegestao.repository;

import br.com.coffeegestao.database.ConnectionFactory;
import br.com.coffeegestao.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class UsuarioRepository {

    public Usuario salvar(Usuario usuario) {
        String sql = """
                INSERT INTO usuarios (nome, email, senha, perfil, ativo)
                VALUES (?, ?, ?, ?, ?);
                """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPerfil());
            stmt.setInt(5, usuario.isAtivo() ? 1 : 0);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                }
            }

            return usuario;

        }   catch (Exception e) {
            throw new RuntimeException("Erro ao salvar usuário no banco de dados.", e);
        }
    }
}
