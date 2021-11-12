create table book
(
   id bigint not null,
   title varchar(255) not null,
   primary key(id)
);

create table book_review
(
   id bigint not null,
   book_id bigint not null,
   review varchar(4000) not null,
   written_by varchar(255) not null
   primary key(id),
   foreign key (book_id) references book(id)
);