CREATE TABLE IF NOT EXISTS produto (
  id INT AUTO_INCREMENT,
  produto VARCHAR(255) NOT NULL,
  preco DOUBLE PRECISION NOT NULL,
  imagem VARCHAR(255) NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  marca_produto VARCHAR(255) NOT NULL,
  categoria_id INT,
  fornecedor_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (categoria_id) REFERENCES categoria(id),
  FOREIGN KEY (fornecedor_id) REFERENCES usuario(id)
)