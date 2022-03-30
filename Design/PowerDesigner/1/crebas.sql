create table ADMIN (
USERNAME_ADMIN       VARCHAR(64)                    not null,
HASHPASS_ADMIN       VARCHAR(64)                    not null,
NAMA_ADMIN           VARCHAR(64)                    not null,
SESI_ADMIN           VARCHAR(64),
PING_SESI_ADMIN      DATE,
primary key (USERNAME_ADMIN)
);

create unique index ADMIN_PK on ADMIN (
USERNAME_ADMIN ASC
);

create table KABEL (
NOMOR_KABEL          INTEGER                        not null,
STATUS_KABEL         INTEGER                        not null,
KETERANGAN_KABEL     VARCHAR(256),
primary key (NOMOR_KABEL)
);

create unique index KABEL_PK on KABEL (
NOMOR_KABEL ASC
);

create table LCD (
NOMOR_LCD            INTEGER                        not null,
STATUS_LCD           INTEGER                        not null,
KETERANGAN_LCD       VARCHAR(256),
primary key (NOMOR_LCD)
);

create table PEMINJAMAN (
NOMOR_PEMINJAMAN     INTEGER                        not null,
NOMOR_KABEL          INTEGER,
NOMOR_LCD            INTEGER                        not null,
STATUS_PEMINJAMAN    INTEGER                        not null,
TANGGAL_PEMINJAMAN   DATE                           not null,
NAMA_PEMINJAM        VARCHAR(64)                    not null,
NIM_PEMINJAM         CHAR(9)                        not null,
NO_HP_PEMINJAM       VARCHAR(14)                    not null,
KETERANGAN_PEMINJAMAN VARCHAR(256),
primary key (NOMOR_PEMINJAMAN),
foreign key (NOMOR_KABEL)
      references KABEL (NOMOR_KABEL),
foreign key (NOMOR_LCD)
      references LCD (NOMOR_LCD)
);

create table KELAS_PINJAM (
ID_KELAS_PINJAM      INTEGER                        not null,
NOMOR_PEMINJAMAN     INTEGER                        not null,
NAMA_DOSEN           VARCHAR(64)                    not null,
NAMA_MATA_KULIAH     VARCHAR(64)                    not null,
JAM_MULAI            TIME                           not null,
JAM_SELESAI          TIME                           not null,
primary key (ID_KELAS_PINJAM),
foreign key (NOMOR_PEMINJAMAN)
      references PEMINJAMAN (NOMOR_PEMINJAMAN)
);

create unique index KELAS_PINJAM_PK on KELAS_PINJAM (
ID_KELAS_PINJAM ASC
);

create  index PINJAM_UNTUK_KELAS_FK on KELAS_PINJAM (
NOMOR_PEMINJAMAN ASC
);

create unique index LCD_PK on LCD (
NOMOR_LCD ASC
);

create table NOMOR_HP_NONAKTIF (
NOMOR_HP             VARCHAR(14)                    not null,
KETERANGAN           VARCHAR(256),
primary key (NOMOR_HP)
);

create unique index NOMOR_HP_NONAKTIF_PK on NOMOR_HP_NONAKTIF (
NOMOR_HP ASC
);

create unique index PEMINJAMAN_PK on PEMINJAMAN (
NOMOR_PEMINJAMAN ASC
);

create  index KABEL_DIPINJAM_FK on PEMINJAMAN (
NOMOR_KABEL ASC
);

create  index RELATIONSHIP_2_FK on PEMINJAMAN (
NOMOR_LCD ASC
);

