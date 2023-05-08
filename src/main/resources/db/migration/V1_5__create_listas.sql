CREATE TABLE IF NOT EXISTS lista (
  id INT AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  consumidor_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (consumidor_id) REFERENCES usuario(id)
)
