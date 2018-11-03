CREATE TABLE pessoa
(
  id bigserial PRIMARY KEY NOT NULL,
  nome text NOT NULL,
  ativo boolean default false,
  logradouro text,
  numero text,
  bairro text,
  cep text,
  cidade text,
  estado text
);

INSERT INTO pessoa (nome, ativo, logradouro, numero, bairro, cep, cidade,  Estado) VALUES ('Luís', true, 'Herculano Olimpia', '152', 'Jardim Madalena','13236658','Novopolis','SP');
INSERT INTO pessoa (nome, ativo, logradouro, numero, bairro, cep, cidade,  Estado) VALUES ('André', true, 'capixaba', '1892', 'Jardim Santana','13236895','Novopolis','SP');
INSERT INTO pessoa (nome, ativo, logradouro, numero, bairro, cep, cidade,  Estado) VALUES ('Junior', true, 'Coliseu', '12', 'Jardim Europa','13236669','Novopolis','SP');
INSERT INTO pessoa (nome, ativo, logradouro, numero, bairro, cep, cidade,  Estado) VALUES ('João', true, 'Herculano Olimpia', '152', 'Jardim Madalena','13236658','Novopolis','SP');

