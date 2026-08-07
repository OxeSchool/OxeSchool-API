CREATE TABLE usuarios(
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(50) NOT NULL,
                         email VARCHAR(50) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL
);

INSERT INTO usuarios (id, nome, email, senha) SELECT id, nome, email, senha FROM alunos;
INSERT INTO usuarios (id, nome, email, senha) SELECT id, nome, email, senha FROM professores;

SELECT setval('usuarios_id_seq', (SELECT MAX(id) FROM usuarios));

ALTER TABLE alunos ALTER COLUMN id DROP DEFAULT;
ALTER TABLE alunos DROP CONSTRAINT alunos_pkey;
ALTER TABLE alunos ADD PRIMARY KEY (id);
ALTER TABLE alunos ADD CONSTRAINT fk_alunos_usuarios FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE;
ALTER TABLE alunos DROP COLUMN nome, DROP COLUMN email, DROP COLUMN senha, DROP COLUMN cursos_Ids;

ALTER TABLE professores ALTER COLUMN id DROP DEFAULT;
ALTER TABLE professores DROP CONSTRAINT professores_pkey;
ALTER TABLE professores ADD PRIMARY KEY (id);
ALTER TABLE professores ADD CONSTRAINT fk_professores_usuarios FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE;
ALTER TABLE professores DROP COLUMN nome, DROP COLUMN email, DROP COLUMN senha;