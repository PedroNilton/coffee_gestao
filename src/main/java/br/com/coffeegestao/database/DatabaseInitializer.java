package br.com.coffeegestao.database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        String sqlUsuarios = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    senha TEXT NOT NULL,
                    perfil TEXT NOT NULL,
                    ativo INTEGER NOT NULL DEFAULT 1
                );
                """;

        String sqlClientes = """
                CREATE TABLE IF NOT EXISTS clientes (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      nome TEXT NOT NULL,
                      telefone TEXT,
                      cpf TEXT,
                      email TEXT,
                      endereco TEXT
                );
                """;

        String sqlAparelhos = """
                CREATE TABLE IF NOT EXISTS aparelhos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    cliente_id INTEGER NOT NULL,
                    tipo TEXT NOT NULL,
                    marca TEXT,
                    modelo TEXT,
                    numero_serie TEXT,
                    observacoes TEXT,
                    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
                );
                """;

        String sqlOrdens = """
                CREATE TABLE IF NOT EXISTS ordens_servico (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    cliente_id INTEGER NOT NULL,
                    aparelho_id INTEGER NOT NULL,
                    defeito_relatado TEXT NOT NULL,
                    diagnostico TEXT,
                    solucao TEXT,
                    status TEXT NOT NULL,
                    valor_servico REAL DEFAULT 0,
                    data_abertura TEXT NOT NULL,
                    data_fechamento TEXT,
                    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
                    FOREIGN KEY (aparelho_id) REFERENCES aparelhos(id)
                );
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlUsuarios);
            stmt.execute(sqlClientes);
            stmt.execute(sqlAparelhos);
            stmt.execute(sqlOrdens);

            System.out.println("Banco inicializado com sucesso.");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar banco de dados",e);
        }
    }
}
