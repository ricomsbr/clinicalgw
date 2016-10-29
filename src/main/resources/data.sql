-- USER
INSERT INTO ackta_user (id, active, version, name, username, password) SELECT 1, 1, 0, 'Desenvolvedor', 'desenv', 'senha1' FROM DUAL; 
INSERT INTO ackta_user (id, active, version, name, username, password) SELECT 2, 1, 0, 'Administrador', 'admin', 'senha2' FROM DUAL;

-- ROLE
INSERT INTO role (id, active, version, name) SELECT 1, 1, 0, 'ROLE_ADMIN' FROM DUAL; 
INSERT INTO role (id, active, version, name) SELECT 2, 1, 0, 'ROLE_USER' FROM DUAL; 
INSERT INTO role (id, active, version, name) SELECT 3, 1, 0, 'ROLE_APPROVER' FROM DUAL; 
INSERT INTO role (id, active, version, name) SELECT 4, 1, 0, 'ROLE_INTERNAL' FROM DUAL; 
INSERT INTO role (id, active, version, name) SELECT 5, 1, 0, 'ROLE_EXTERNAL' FROM DUAL; 

-- USER_ROLE
INSERT INTO user_role (id_user, id_role) values (1,2); 
INSERT INTO user_role (id_user, id_role) values (1,4); 
INSERT INTO user_role (id_user, id_role) values (2,1); 
INSERT INTO user_role (id_user, id_role) values (2,2); 
INSERT INTO user_role (id_user, id_role) values (2,3); 