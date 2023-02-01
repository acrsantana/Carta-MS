CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE cartas (
                               id uuid NOT NULL DEFAULT uuid_generate_v4(),
                               esforco float4 NOT NULL,
                               id_organizacao int4 NOT NULL,
                               valor varchar(255) NULL,
                               CONSTRAINT cartas_pkey PRIMARY KEY (id)
);
INSERT INTO cartas (esforco, id_organizacao, valor) VALUES (2, 1, '2');