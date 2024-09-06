package main.cadastro;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();

        // Cadastrar clientes
        Cliente cliente1 = new Cliente("João Silva", "Rua A, 123", "123.456.789-00", "joao@example.com", "1234-5678");
        Cliente cliente2 = new Cliente("Maria Oliveira", "Avenida B, 456", "987.654.321-00", "maria@example.com", "8765-4321");

        try {
            clienteDAO.cadastrarCliente(cliente1);
            clienteDAO.cadastrarCliente(cliente2);

            // Atualizar cliente
            Cliente clienteAtualizado = new Cliente("João Silva", "Rua Nova, 123", "123.456.789-00", "joao.novo@example.com", "4321-8765");
            clienteDAO.atualizarCliente(clienteAtualizado);

            // Ler dados do cliente
            Cliente cliente = clienteDAO.lerCliente("123.456.789-00");
            System.out.println(cliente);

            // Remover cliente
            clienteDAO.removerCliente("987.654.321-00");

            // Listar todos os clientes
            for (Cliente c : clienteDAO.listarClientes()) {
                System.out.println(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

