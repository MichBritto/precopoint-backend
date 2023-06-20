-- Adicionar Usuários - FORNECEDORES
INSERT INTO usuario (email,cep,nome,senha,cnpj,logotipo,status_conta) VALUES
	 ('carrefour@gmail.com','09520180','Carrefour','$2a$10$JV.WizaD91vkm/dUMb90AOWi0LbgB2kU4kyUj2nTHlE92F9ZJfLfW','522.222.222-0',
	 'https://logodownload.org/wp-content/uploads/2015/02/carrefour-logo-1-1.png',1),
	 ('extra@gmail.com','09521320','Extra','$2a$10$qjOn9nmfJHxLuqhc.2PyReB8CH64Rx4AGNEtg9v7d6QggK/xdDAr.','522.222.222-0',
	 'https://logodownload.org/wp-content/uploads/2014/12/extra-logo-mercado-5.png',1),
	 ('semar@gmail.com','03620001','Semar','$2a$10$oXyVpcbnzGFUB78bL9xH2O0/k1HL8m7oFgkgAY5RmxYFFnBcMptBm','522.222.222-0',
	 'https://logodownload.org/wp-content/uploads/2021/07/semar-supermercados-logo-0.png',1),
	 ('lourencini@gmail.com','09330150','Lourencini','$2a$10$M2R3CDYFWmHt9NpZ2aXo0uFU1JGg7eXK8zGMSNd7oJtkmyeD6pJOa','522.222.222-0',
	 'https://www.lourencini.com.br/Imagens/img-topo-logotipo-lourencini.png',1),
	 ('atacadao@gmail.com','09581030','Atacadao','$2a$10$jhfjQzmx7QwWnVa3EZJbkOtSBPllq.VbjCW2tf0iUkJYCPM.sxKzC','522.222.222-0',
	 'https://logospng.org/download/atacadao/atacadao-4096.png',1);
-- Adicionar Usuários - CONSUMIDORES
INSERT INTO usuario (email,cep,nome,senha,status_conta) VALUES
	 ('marcos@gmail.com','08450550','Marcos Vinicios','$2a$10$kwGMntH5KTNKX2SNJ.mxD.q2yE6NT/xekfzHf4uv7R2Nm83v.JGl6',1),
	 ('gustavo@gmail.com','08470430','Gustavo Hide','$2a$10$rBPPs.cAFQf5pKqbRZjnYOdyNPJbJor1Dp2Uqkh/hROwoWjd1WR8W',1),
	 ('michel@gmail.com','09340580','Michel Brito','$2a$10$XkCYEtUMSTqXMtN0s9EDCuA0WFU46bmq9SfE0VpLk4eOQMsJqWJMe',1),
	 ('joao@gmail.com','09132680','Joao Custodio','$2a$10$FxTmyhUJ.G5JJkhFtta2bO9dbUPOZNNlJOo0yi36VKcVLv/XKcZuC',1);
-- Adicionar Usuários - ADMINISTRADOR
INSERT INTO usuario (email,nome,senha,status_conta) VALUES
	 ('precopoint@gmail.com','PrecoPoint','$2a$10$I8EtRfsmtdV.T2XWbBYZve/s7QRxwWyLt/2m6MIiu5RjYYxgDBGvC',1);

