CREATE TABLE tarefa_tipo (
 id integer NOT NULL PRIMARY KEY,
 nome varchar(255) NOT NULL
);
CREATE TABLE tarefa (
 id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
 nome varchar(255) NOT NULL,
 tarefa_tipo_id integer NOT NULL,
 data varchar(10) NOT NULL,
 FOREIGN KEY (tarefa_tipo_id) REFERENCES tarefa_tipo (id)
);