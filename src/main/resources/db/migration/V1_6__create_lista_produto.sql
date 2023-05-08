CREATE TABLE IF NOT EXISTS lista_produto (
  id INT AUTO_INCREMENT PRIMARY KEY,
  lista_id INT NOT NULL,
  produto_id INT NOT NULL,
  qtde INT NOT NULL,
  FOREIGN KEY (lista_id) REFERENCES lista(id),
  FOREIGN KEY (produto_id) REFERENCES produto(id)
)
