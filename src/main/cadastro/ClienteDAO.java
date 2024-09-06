package main.cadastro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class ClienteDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/cadastro_clientes";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String INSERT_CLIENTE_SQL = "INSERT INTO cliente (nome, endereco, documento, email, telefone) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_CLIENTE_BY_DOCUMENTO = "SELECT id, nome, endereco, documento, email, telefone FROM cliente WHERE documento = ?;";
    private static final String SELECT_ALL_CLIENTES = "SELECT * FROM cliente;";
    private static final String DELETE_CLIENTE_SQL = "DELETE FROM cliente WHERE documento = ?;";
    private static final String UPDATE_CLIENTE_SQL = "UPDATE cliente SET nome = ?, endereco = ?, email = ?, telefone = ? WHERE documento = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Cadastrar cliente
    public void cadastrarCliente(Cliente cliente) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENTE_SQL)) {
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getEndereco());
            preparedStatement.setString(3, cliente.getDocumento());
            preparedStatement.setString(4, cliente.getEmail());
            preparedStatement.setString(5, cliente.getTelefone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    // Atualizar cliente
    public boolean atualizarCliente(Cliente cliente) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENTE_SQL)) {
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEndereco());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getDocumento());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // Ler dados do cliente
    public Cliente lerCliente(String documento) {
        Cliente cliente = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENTE_BY_DOCUMENTO)) {
            preparedStatement.setString(1, documento);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                cliente = new Cliente(nome, endereco, documento, email, telefone);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return cliente;
    }

    // Remover cliente
    public boolean removerCliente(String documento) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CLIENTE_SQL)) {
            statement.setString(1, documento);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    // Listar todos os clientes
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENTES)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String documento = rs.getString("documento");
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                clientes.add(new Cliente(nome, endereco, documento, email, telefone));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return clientes;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}

