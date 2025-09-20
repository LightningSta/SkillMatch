create table security_persons(
                                 id bigserial,
                                 username varchar not null,
                                 password varchar not null,
                                 role varchar not null
);

CREATE TABLE analize_entity (
                                id BIGSERIAL PRIMARY KEY,
                                file BYTEA NOT NULL,
                                result TEXT
);
insert into security_persons(username, password, role) values ('test','$2a$10$YD/9e0GtIHQMtYpLEE0qCetaQ9mdx4du2Y5pKJs00GiqNAJrAUrBS','Admin')