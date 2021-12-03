
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(25) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    firstname VARCHAR(30),
    lastname VARCHAR(50),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    last_login_at TIMESTAMP,
    created_by VARCHAR(25) NOT NULL,
    updated_by VARCHAR(25),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_roles CASCADE;
CREATE TABLE user_roles (
	user_id BIGINT NOT NULL,
	role VARCHAR(30) NOT NULL,
	CONSTRAINT user_roles_pk PRIMARY KEY (user_id, role),
	CONSTRAINT user_roles_fk1 FOREIGN KEY (user_id)
        REFERENCES users(id) ON DELETE CASCADE ON UPDATE NO ACTION
);


DROP TABLE IF EXISTS contracts CASCADE;
CREATE TABLE contracts (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    content TEXT NOT NULL,
    description VARCHAR(250),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_by VARCHAR(25) NOT NULL,
    updated_by VARCHAR(25),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT contracts_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS contacts CASCADE;
CREATE TABLE contacts (
	id BIGINT AUTO_INCREMENT NOT NULL,
	contact_type VARCHAR(30) NOT NULL,
	name VARCHAR(80) NOT NULL,
	description VARCHAR(200),
	CONSTRAINT contacts_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT NOT NULL,
    fullname VARCHAR(80) NOT NULL,
    cpf_cnpj VARCHAR(20),
    cep VARCHAR(9),
    address VARCHAR(50),
    address_complement VARCHAR(100),
    district VARCHAR(50),
    city VARCHAR(50),
    state VARCHAR(2),
    created_by VARCHAR(25) NOT NULL,
    updated_by VARCHAR(25),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT customers_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS customer_contacts CASCADE;
CREATE TABLE customer_contacts (
	customer_id BIGINT NOT NULL,
	contact_id BIGINT NOT NULL,
	CONSTRAINT customer_contacts_pk PRIMARY KEY (customer_id, contact_id),
	CONSTRAINT customer_contacts_fk1 FOREIGN KEY (customer_id)
        REFERENCES customers(id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT customer_contacts_fk2 FOREIGN KEY (contact_id)
        REFERENCES contacts(id) ON DELETE CASCADE ON UPDATE NO ACTION
);


DROP TABLE IF EXISTS places CASCADE;
CREATE TABLE places (
	id BIGINT AUTO_INCREMENT NOT NULL,
	name VARCHAR(80) NOT NULL,
	value DECIMAL(6,2) NOT NULL,
	place_type VARCHAR(30) NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
	created_by VARCHAR(25) NOT NULL,
    updated_by VARCHAR(25),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
	CONSTRAINT additionals_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS additionals CASCADE;
CREATE TABLE additionals (
	id BIGINT AUTO_INCREMENT NOT NULL,
	name VARCHAR(200) NOT NULL,
	value DECIMAL(6,2) NOT NULL,
	required BOOLEAN NOT NULL DEFAULT FALSE,
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
	created_by VARCHAR(25) NOT NULL,
    updated_by VARCHAR(25),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
	CONSTRAINT additionals_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS additional_packages CASCADE;
CREATE TABLE additional_packages (
	additional_package_id BIGINT AUTO_INCREMENT NOT NULL,
	additional_id BIGINT NOT NULL,
	description TEXT NOT NULL,
	CONSTRAINT additional_packages_pk PRIMARY KEY (additional_package_id),
	CONSTRAINT additional_packages_fk1 FOREIGN KEY (additional_id)
        REFERENCES additionals(id) ON DELETE CASCADE ON UPDATE NO ACTION
);



