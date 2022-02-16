-- call next value for hibernate_sequence;
insert into user (`id`,`name`,`email`,`created_at`,`updated_at`) values (1, 'martin','martin@fast.com',now(),now());

-- call next value for hibernate_sequence;
insert into user (`id`,`name`,`email`,`created_at`,`updated_at`) values (2, 'demis','demis@fast.com',now(),now());

-- call next value for hibernate_sequence;
insert into user (`id`,`name`,`email`,`created_at`,`updated_at`) values (3, 'sopia','sopia@slow.com',now(),now());

-- call next value for hibernate_sequence;
insert into user (`id`,`name`,`email`,`created_at`,`updated_at`) values (4, 'james','james@slow.com',now(),now());

-- call next value for hibernate_sequence;
insert into user (`id`,`name`,`email`,`created_at`,`updated_at`) values (5, 'martin','james@another.com',now(),now());

insert into publisher(`id`,`name`) value (1, '빠른대학');
insert into book(`id`,`name`,`publisher_id`, `deleted`, `status`) values (2,'Spring Security 작은격차 클래스', 1,false, 200);
insert into book(`id`,`name`,`publisher_id`, `deleted`, `status`) values (3,'SpringBoot 하나인 클래스', 1,true, 100);
insert into book(`id`,`name`,`publisher_id`, `deleted`, `status`) values (1,'JPA 작은격차 클래스', 1,false, 100);
