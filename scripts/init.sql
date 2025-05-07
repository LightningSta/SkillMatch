create table security_persons(
                                 id bigserial,
                                 username varchar not null,
                                 password varchar not null,
                                 role varchar not null
);
insert into security_persons(username, password, role) values ('test','$2a$10$YD/9e0GtIHQMtYpLEE0qCetaQ9mdx4du2Y5pKJs00GiqNAJrAUrBS','Admin')