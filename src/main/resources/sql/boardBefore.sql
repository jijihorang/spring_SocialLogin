drop table if exists tbl_reply_favorite;

drop table if exists tbl_reply;

drop table if exists tbl_board_attach;

drop table if exists tbl_board;

create table tbl_board
(
    bno      int auto_increment
        primary key,
    title    varchar(300)                          not null,
    content  text                                  not null,
    writer   varchar(50)                           not null,
    regDate  timestamp default current_timestamp() null,
    modDate  timestamp default current_timestamp() null,
    replyCnt int       default 0                   null,
    tag      varchar(500)                          null,
    viewCnt  int       default 0                   null
);

create table tbl_board_attach
(
    bno      int           null,
    fileName varchar(200)  not null,
    ord      int default 0 null,
    constraint fk_board
        foreign key (bno) references tbl_board (bno)
);

create index idx_board
    on tbl_board_attach (bno desc, ord asc);


create table tbl_reply_favorite (
                                    rno int not null,
                                    mid varchar(50) not null,
                                    regDate timestamp default now()
);

alter table tbl_reply_favorite
    add constraint pk_reply_favorite
        primary key (rno, mid)
;

create table tbl_reply
(
    rno     int auto_increment
        primary key,
    bno     int                                   not null,
    reply   varchar(500)                          not null,
    replyer varchar(50)                           not null,
    regDate timestamp default current_timestamp() null,
    modDate timestamp default current_timestamp() null,
    constraint fk_board_reply
        foreign key (bno) references tbl_board (bno)
);

select rno, min(replyText), min(replyer), count(fno), sum(choice)
from (
         select reply.rno rno, reply replyText, replyer, fa.rno fno,
                if(fa.mid = 'r1', 1, 0) choice
         from
             tbl_reply reply left join tbl_reply_favorite fa on reply.rno = fa.rno
         where
             reply.bno = 100
     ) r1
group by rno
;


create index idx_board
    on tbl_reply (bno desc, rno asc);



