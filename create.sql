CREATE TABLE IF NOT EXISTS produto (
id int auto_increment primary key,
nomeproduto varchar(255),
preco decimal(15,2),
fornecedor varchar(255)
);