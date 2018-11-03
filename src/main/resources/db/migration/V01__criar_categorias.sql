CREATE TABLE categoria
(
  id bigserial PRIMARY KEY NOT NULL,
  nome text NOT NULL
);


INSERT INTO categoria (nome) VALUES ('Lazer');
INSERT INTO categoria (nome) VALUES ('Alimentação');
INSERT INTO categoria (nome) VALUES ('Supermecado');
INSERT INTO categoria (nome) VALUES ('Farmacia');
INSERT INTO categoria (nome) VALUES ('Transporte');
INSERT INTO categoria (nome) VALUES ('Outros');
