CREATE DATABASE cadastro_clientes;
USE cadastro_clientes;

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    endereco VARCHAR(255),
    documento VARCHAR(20) UNIQUE,
    email VARCHAR(100),
    telefone VARCHAR(20)
);
