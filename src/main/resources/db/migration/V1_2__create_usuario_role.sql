CREATE TABLE usuario_role (
  usuario_id INT NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (usuario_id, role_id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (role_id) REFERENCES role(id)
)