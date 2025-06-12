CREATE DATABASE IF NOT EXISTS alertacidadao_db;


USE alertacidadao_db;

CREATE TABLE IF NOT EXISTS tb_usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_localizacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    endereco VARCHAR(255),
    numero INT
);


CREATE TABLE IF NOT EXISTS tb_alerta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    descricao TEXT NOT NULL,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    esta_ativo BOOLEAN NOT NULL,
    id_localizacao INT NOT NULL,
    id_criador INT NOT NULL,
    FOREIGN KEY (id_localizacao) REFERENCES tb_localizacao(id),
    FOREIGN KEY (id_criador) REFERENCES tb_usuario(id)
);


CREATE TABLE IF NOT EXISTS tb_comentario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    texto TEXT NOT NULL,
    data_hora DATETIME NOT NULL,
    id_usuario INT NOT NULL,
    id_alerta INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id),
    FOREIGN KEY (id_alerta) REFERENCES tb_alerta(id)
);

CREATE TABLE IF NOT EXISTS tb_votacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    voto BOOLEAN, -- Representa a opinião do usuário (true = 'ainda acontece', false = 'não acontece mais')
    ainda_acontecendo BOOLEAN NOT NULL, -- Campo da sua classe Votacao
    data DATE,
    hora TIME,
    id_usuario INT NOT NULL,
    id_alerta INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id),
    FOREIGN KEY (id_alerta) REFERENCES tb_alerta(id)
);


CREATE TABLE IF NOT EXISTS tb_reacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    data DATE NOT NULL,
    id_autor INT NOT NULL,
    id_alerta INT,
    id_comentario INT,
    FOREIGN KEY (id_autor) REFERENCES tb_usuario(id),
    FOREIGN KEY (id_alerta) REFERENCES tb_alerta(id),
    FOREIGN KEY (id_comentario) REFERENCES tb_comentario(id),
    -- Esta restrição garante que uma reação não possa pertencer a um alerta E a um comentário ao mesmo tempo.
    CONSTRAINT chk_reacao_target CHECK ((id_alerta IS NOT NULL AND id_comentario IS NULL) OR (id_alerta IS NULL AND id_comentario IS NOT NULL))
);

USE alertacidadao_db;
SELECT * FROM tb_localizacao;
SELECT * FROM tb_usuario;
SELECT * FROM tb_alerta;
SELECT * FROM tb_comentario;
SHOW TABLES;