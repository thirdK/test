DROP TABLE TBL_BOARD CASCADE CONSTRAINTS ;
DROP TABLE TBL_USER CASCADE CONSTRAINTS ;

DROP SEQUENCE SEQ_BOARD;
DROP SEQUENCE SEQ_USER;

CREATE SEQUENCE SEQ_USER;
CREATE SEQUENCE SEQ_BOARD;

CREATE TABLE TBL_USER(
                         USER_ID NUMBER,
                         LOGIN_ID VARCHAR2(200),
                         PASSWORD VARCHAR2(200),
                         GENDER CHAR(1),
                         EMAIL VARCHAR2(200),
                         ADDRESS VARCHAR2(1000),
                         ADDRESS_DETAIL VARCHAR2(1000),
                         ZIPCODE VARCHAR2(1000),
                         CONSTRAINT PK_USER PRIMARY KEY (USER_ID)
);

CREATE TABLE TBL_BOARD(
                          BOARD_ID NUMBER,
                          TITLE VARCHAR2(500),
                          CONTENT VARCHAR2(1000),
                          CREATED_DATE DATE DEFAULT SYSDATE,
                          MODIFIED_DATE DATE DEFAULT SYSDATE,
                          USER_ID NUMBER,
                          CONSTRAINT PK_BOARD PRIMARY KEY (BOARD_ID),
                          CONSTRAINT FK_BOARD FOREIGN KEY (USER_ID)
                              REFERENCES TBL_USER(USER_ID)
);

-------------------------------------------------------------------
-- 파일 테이블
DROP SEQUENCE  SEQ_FILE;
CREATE SEQUENCE SEQ_FILE;

DROP TABLE TBL_FILE CASCADE CONSTRAINTS ;
CREATE TABLE TBL_FILE(
                         FILE_ID NUMBER,
                         NAME VARCHAR2(1000),
                         UPLOAD_PATH VARCHAR2(1000),
                         UUID VARCHAR2(1000),
                         BOARD_ID NUMBER,
                         CONSTRAINT PK_FILE PRIMARY KEY (FILE_ID),
                         CONSTRAINT FK_FILE FOREIGN KEY (BOARD_ID)
                             REFERENCES TBL_BOARD(BOARD_ID)
);

/*
 UUID
 중복이 가능한 id(식별자)이다.
 그러나 중복 될 확률이 매우매우 낮기 때문에 중복이 없다고 봐도 무방한 값이다.
 사용자가 파일을 업로드할 때 같은 이름의 파일을 올리면 파일끼리 충돌이 발생하므로
 이름 앞에 UUID 연결하여 중복되는 이름이 없도록 만들어준다.
 */

-----------------------------------------------------------------------
DROP SEQUENCE SEQ_REPLY;
CREATE SEQUENCE SEQ_REPLY;

DROP TABLE TBL_REPLY CASCADE CONSTRAINTS ;
CREATE TABLE TBL_REPLY(
                          REPLY_ID NUMBER,
                          CONTENT VARCHAR2(1000),
                          CREATED_DATE DATE DEFAULT SYSDATE,
                          MODIFIED_DATE DATE DEFAULT SYSDATE,
                          BOARD_ID NUMBER,
                          USER_ID NUMBER,
                          CONSTRAINT PK_REPLY PRIMARY KEY (REPLY_ID),
                          CONSTRAINT FK_REPLY_BOARD FOREIGN KEY (BOARD_ID)
                              REFERENCES TBL_BOARD(BOARD_ID) ON DELETE CASCADE,
                          CONSTRAINT FK_REPLY_USER FOREIGN KEY (USER_ID)
                              REFERENCES TBL_USER(USER_ID) ON DELETE CASCADE
);


















