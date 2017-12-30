#sudo apt-get update -y;
#sudo apt-get install mysql-server -y;
#sudo mysql_secure_installation -y;
#sudo mysql_install_db -y;

sudo mysql -u root -e "drop user test@localhost;";
sudo mysql -u root -e "create user test@localhost identified by '123456';";
sudo mysql -u root -e "grant all privileges on *.* to 'test'@'localhost';"
sudo mysql -u test --password="123456" -e "drop database fj21;";
sudo mysql -u test --password="123456" -e "create database fj21;";
sudo mysql -u test --password="123456" -e "
	create table contatos (
		id BIGINT NOT NULL AUTO_INCREMENT,
		nome VARCHAR(255),
		email VARCHAR(255),
		endereco VARCHAR(255),
		dataNascimento DATE,
		primary key (id)
	);
" fj21;

# Para mostrar o banco de dados:
#mysql -u test --password="123456" -e "select * from contatos;" fj21;
