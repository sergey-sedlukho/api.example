INSERT INTO admin (id, name, status) VALUES ((select NEXTVAL ('seq_admin')), 'admin', 1);

INSERT INTO api_user (id, name, is_valid) VALUES ((select NEXTVAL ('seq_user')), 'user', true);

INSERT INTO customer (id, c_user_fk, m_user_fk, name, surname, is_valid) VALUES ((select NEXTVAL ('seq_customer')), 1, NULL, 'name_1', 'surname_1', true);
INSERT INTO customer (id, c_user_fk, m_user_fk, name, surname, is_valid) VALUES ((select NEXTVAL ('seq_customer')), 1, NULL, 'name_2', 'surname_2', true);
INSERT INTO customer (id, c_user_fk, m_user_fk, name, surname, is_valid) VALUES ((select NEXTVAL ('seq_customer')), 1, NULL, 'name_3', 'surname_3', true);