insert into projects(id, type) VALUES (1, 'Заваривание чая');

insert into tasks(id, project_id, type, task_order) values (1, 1, 'Подготовить кружки', 1),
                                               (2, 1, 'Вскипятить воду', 2),
                                               (3, 1, 'Положить заварку', 3),
                                               (4, 1, 'Положить мяту', 4),
                                               (5, 1, 'Налить кипяток', 5);

insert into conditions(id, project_id, description, type) values (1, 1, 'Если температура ' ||
                                                                'воды меньше 100, ее нужно вскипятить',
                                                          'Температура воды'),
                                                            (2, 1, 'Если мяты нет, ее нужно купить', 'Наличие мяты');