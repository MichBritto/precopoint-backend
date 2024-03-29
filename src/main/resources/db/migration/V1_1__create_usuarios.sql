CREATE TABLE IF NOT EXISTS usuario (
  id INT AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  cep VARCHAR(255),
  email VARCHAR(255) UNIQUE NOT NULL,
  senha VARCHAR(255) NOT NULL,
  status_conta BOOLEAN DEFAULT TRUE,
  criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  atualizado_em TIMESTAMP NULL DEFAULT NULL,
  cnpj VARCHAR(255),
  logotipo VARCHAR(255),
  PRIMARY KEY (id)
)