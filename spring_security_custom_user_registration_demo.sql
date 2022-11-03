
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS users_roles;
--
-- Table structure for table user
--


CREATE TABLE employee (
                        id BIGSERIAL NOT NULL,
                        username varchar(50) NOT NULL,
                        password char(80) NOT NULL,
                        first_name varchar(50) NOT NULL,
                        last_name varchar(50) NOT NULL,
                        email varchar(50) NOT NULL,
                        PRIMARY KEY (id)
);

--
-- Dumping data for table user
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- Default passwords here are: fun123
--

INSERT INTO employee (username,password,first_name,last_name,email)
VALUES
    ('john','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','John','Doe','john@gmail.com'),
    ('mary','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Mary','Public','mary@hotmail.com'),
    ('susan','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Susan','Adams','susan@yahoo.com');


--
-- Table structure for table role
--



CREATE TABLE role (
                        id BIGSERIAL NOT NULL ,
                        name varchar(50) DEFAULT NULL,
                        PRIMARY KEY (id)
);

--
-- Dumping data for table roles
--

INSERT INTO role (name)
VALUES
    ('ROLE_EMPLOYEE'),('ROLE_MANAGER'),('ROLE_ADMIN');

--
-- Table structure for table users_roles
--

DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles (
                               user_id BIGINT NOT NULL,
                               role_id BIGINT NOT NULL,

                               PRIMARY KEY (user_id,role_id),

                               CONSTRAINT FK_USER FOREIGN KEY (user_id)
                                   REFERENCES employee (id)
                                   ON DELETE NO ACTION ON UPDATE NO ACTION,

                               CONSTRAINT FK_ROLE FOREIGN KEY (role_id)
                                   REFERENCES role (id)
                                   ON DELETE NO ACTION ON UPDATE NO ACTION
);


--
-- Dumping data for table users_roles
--

INSERT INTO users_roles (user_id,role_id)
VALUES
    (1, 1),
    (2, 1),
    (2, 2),
    (3, 1),
    (3, 2),
    (3, 3)