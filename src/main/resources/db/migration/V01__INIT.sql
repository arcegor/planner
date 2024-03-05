CREATE TABLE if not exists projects (
    id serial not null,
    type varchar(255) unique not null,
    primary key (id)
);

CREATE TABLE if not exists tasks (
    id serial not null,
    project_id serial not null,
    type varchar(255) unique not null,
    task_order int not null,
    primary key (id),
    foreign key (project_id) references projects
);

CREATE TABLE if not exists conditions (
    id serial not null,
    task_id serial not null,
    description varchar(255) default 'Описание правила',
    type varchar(255) unique not null,
    primary key (id),
    foreign key (task_id) references tasks
);