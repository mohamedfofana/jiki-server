--************** CLEAN DATABASE **************--
-- DROP SEQUENCES
DROP SEQUENCE user_seq;
DROP SEQUENCE chart_seq;
DROP SEQUENCE dailymeeting_seq;
DROP SEQUENCE sprint_seq;
DROP SEQUENCE story_seq;
DROP SEQUENCE backlog_seq;
DROP SEQUENCE team_seq;
DROP SEQUENCE attachement_seq;

-- DROP CONSTRAINTS or else trucante won't work 
ALTER TABLE T_ATTACHEMENT DROP CONSTRAINT fk_story_attachement;
ALTER TABLE T_STORY DROP CONSTRAINT fk_reporter_story;
ALTER TABLE T_STORY DROP CONSTRAINT fk_sprint_story;
ALTER TABLE T_STORY DROP CONSTRAINT fk_backlog_story;
ALTER TABLE T_STORY DROP CONSTRAINT fk_assigned_team_story;
ALTER TABLE T_STORY DROP CONSTRAINT fk_assigned_user_story;
ALTER TABLE T_CHART DROP CONSTRAINT fk_sprint_chart;
ALTER TABLE T_SPRINT DROP CONSTRAINT fk_sprint_reporter;
ALTER TABLE T_USER DROP CONSTRAINT fk_user_team;

-- TRUNCATE TABLE  
TRUNCATE TABLE T_ATTACHEMENT;
TRUNCATE TABLE T_STORY;
TRUNCATE TABLE T_CHART;
TRUNCATE TABLE T_DAILYMEETING;
TRUNCATE TABLE T_BACKLOG;
TRUNCATE TABLE T_SPRINT;
TRUNCATE TABLE T_USER;
TRUNCATE TABLE T_TEAM;

-- DROP TABLES
DROP TABLE T_ATTACHEMENT;
DROP TABLE T_STORY;
DROP TABLE T_CHART;
DROP TABLE T_DAILYMEETING;
DROP TABLE T_BACKLOG;
DROP TABLE T_SPRINT;
DROP TABLE T_USER;
DROP TABLE T_TEAM;

--************** CREATE DATABASE **************--

-- CREATE TABLES
CREATE TABLE t_team( 	
    id            			INTEGER NOT NULL,
    name          			VARCHAR2(255) NOT NULL, 
    status         			VARCHAR2(255), 
    creation_date        	DATE, 
    update_date           	DATE,
    CONSTRAINT pk_team PRIMARY KEY (id) 
 );

CREATE TABLE t_user( 
    id            			INTEGER NOT NULL,
	team_id            		INTEGER NOT NULL,	 
    firstname          		VARCHAR2(255) NOT NULL, 
    lastname          	 	VARCHAR2(255) NOT NULL, 
    username          	 	VARCHAR2(255) NOT NULL,
    password          		VARCHAR2(255) NOT NULL,
    role          		    VARCHAR2(255) NOT NULL,
    status         			VARCHAR2(255), 
    creation_date        	DATE, 
    update_date           	DATE,
    CONSTRAINT pk_user PRIMARY KEY (id),
	CONSTRAINT fk_user_team FOREIGN KEY (team_id) REFERENCES t_team (id)	 
 );
 

CREATE TABLE t_sprint( 
    id            			INTEGER NOT NULL, 
    reporter_id             INTEGER NOT NULL,    
    title          			VARCHAR2(255) NOT NULL, 
    description          	VARCHAR2(255) NOT NULL, 
    status         			VARCHAR2(255), 
    workflow       			VARCHAR2(255), 
    creation_date        	DATE, 
    update_date           	DATE,
    CONSTRAINT pk_sprint PRIMARY KEY (id),
    CONSTRAINT fk_sprint_reporter FOREIGN KEY (reporter_id) REFERENCES t_user (id)	
);
CREATE TABLE t_backlog( 
    id            			INTEGER NOT NULL, 
    title          			VARCHAR2(255) NOT NULL, 
    description          	VARCHAR2(255) NOT NULL, 
    status         			VARCHAR2(255), 
    creation_date        	DATE, 
    update_date           	DATE,
    CONSTRAINT pk_backlog PRIMARY KEY (id)
);
CREATE TABLE t_dailymeeting( 
    id            			INTEGER NOT NULL, 
    title          			VARCHAR2(255) NOT NULL, 
    description          	VARCHAR2(255) NOT NULL, 
    status         			VARCHAR2(255), 
    creation_date        	DATE, 
    update_date           	DATE,
    CONSTRAINT pk_dailymeeting PRIMARY KEY (id)
);

CREATE TABLE t_chart( 
    id            			INTEGER NOT NULL, 
    sprint_id            	INTEGER NOT NULL,	
    title          			VARCHAR2(255) NOT NULL, 
    description          	VARCHAR2(255) NOT NULL, 
    report         			VARCHAR2(255), 
    type         			VARCHAR2(255), 
    creation_date        	DATE, 
    update_date           	DATE,
    CONSTRAINT pk_chart PRIMARY KEY (id),
	CONSTRAINT fk_sprint_chart FOREIGN KEY (sprint_id) REFERENCES t_sprint (id)	 
);

CREATE TABLE t_story( 
	id            			INTEGER NOT NULL, 
	reporter_id            	INTEGER NOT NULL, 
	sprint_id            	INTEGER, 
	backlog_id            	INTEGER, 
	assigned_team_id        INTEGER, 
	assigned_user_id        INTEGER, 
    title          			VARCHAR2(255) NOT NULL, 
    description          	VARCHAR2(255) NOT NULL, 
    type         			VARCHAR2(255), 
    status         			VARCHAR2(255), 
    appli_version   		VARCHAR2(255), 
	priority				NUMBER(10),
	businessValue			NUMBER(10),
	storyPoints				NUMBER(10),
	start_date           	DATE,
	end_date           		DATE,
	estimate_end_date       DATE,
    creation_date        	DATE, 
    update_date           	DATE,
    CONSTRAINT pk_story PRIMARY KEY (id),
	CONSTRAINT fk_reporter_story FOREIGN KEY (reporter_id) REFERENCES t_user (id),	
	CONSTRAINT fk_sprint_story FOREIGN KEY (sprint_id) REFERENCES t_sprint (id),	
	CONSTRAINT fk_backlog_story FOREIGN KEY (backlog_id) REFERENCES t_backlog (id),	
	CONSTRAINT fk_assigned_team_story FOREIGN KEY (assigned_team_id) REFERENCES t_team (id),	
	CONSTRAINT fk_assigned_user_story FOREIGN KEY (assigned_user_id) REFERENCES t_user (id)

CREATE TABLE t_attachement( 
    id            			INTEGER NOT NULL, 
    story_id            	INTEGER NOT NULL,	
    title          			VARCHAR2(255) NOT NULL, 
	data					BLOB, 
    creation_date        	DATE, 
    update_date           	DATE,
    CONSTRAINT pk_attachement PRIMARY KEY (id),
	CONSTRAINT fk_story_attachement FOREIGN KEY (story_id) REFERENCES t_story (id)
);

-- CREATE SEQUENCES
CREATE SEQUENCE user_seq
  MINVALUE 100
  MAXVALUE 9999999999
  START WITH 150
  INCREMENT BY 1
  CACHE 5
  NOCYCLE;

CREATE SEQUENCE chart_seq
  MINVALUE 100
  MAXVALUE 9999999999
  START WITH 150
  INCREMENT BY 1
  CACHE 5
  NOCYCLE;

CREATE SEQUENCE dailymeeting_seq
  MINVALUE 100
  MAXVALUE 9999999999
  START WITH 150
  INCREMENT BY 1
  CACHE 5
  NOCYCLE;

CREATE SEQUENCE sprint_seq
  MINVALUE 100
  MAXVALUE 9999999999
  START WITH 150
  INCREMENT BY 1
  CACHE 5
  NOCYCLE;
  
CREATE SEQUENCE story_seq
  MINVALUE 100
  MAXVALUE 9999999999
  START WITH 150
  INCREMENT BY 1
  CACHE 5
  NOCYCLE;

CREATE SEQUENCE backlog_seq
  MINVALUE 100
  MAXVALUE 9999999999
  START WITH 150
  INCREMENT BY 1
  CACHE 5
  NOCYCLE;

CREATE SEQUENCE team_seq
  MINVALUE 100
  MAXVALUE 9999999999
  START WITH 150
  INCREMENT BY 1
  CACHE 5
  NOCYCLE;

CREATE SEQUENCE attachement_seq
  MINVALUE 100
  MAXVALUE 9999999999
  START WITH 150
  INCREMENT BY 1
  CACHE 5
  NOCYCLE;

--***********************************************************
-- INSERT TEAMS
INSERT INTO T_TEAM (ID, NAME, STATUS, CREATION_DATE) VALUES (100,'Master Data Management','ACTIVE', sysdate);
/*
INSERT INTO T_TEAM (ID, NAME, STATUS, CREATION_DATE) VALUES (101,'Master Data Creator','INACTIVE', sysdate);
INSERT INTO T_TEAM (ID, NAME, STATUS, CREATION_DATE) VALUES (102,'Support','ACTIVE', sysdate);
INSERT INTO T_TEAM (ID, NAME, STATUS, CREATION_DATE) VALUES (103,'Securities','ACTIVE', sysdate);
INSERT INTO T_TEAM (ID, NAME, STATUS, CREATION_DATE) VALUES (104,'Legal Entities','ACTIVE', sysdate);
INSERT INTO T_TEAM (ID, NAME, STATUS, CREATION_DATE) VALUES (105,'Legal Entities Controllers','INACTIVE', sysdate);
INSERT INTO T_TEAM (ID, NAME, STATUS, CREATION_DATE) VALUES (106,'Compliance','ACTIVE', sysdate);
INSERT INTO T_TEAM (ID, NAME, STATUS, CREATION_DATE) VALUES (107,'Service Client','ACTIVE', sysdate);
INSERT INTO T_TEAM (ID, NAME, STATUS, CREATION_DATE) VALUES (108,'Development Team','ACTIVE', sysdate);
*/
-- INSERT USERS
/*INSERT INTO T_USER (ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, STATUS, TEAM_ID) VALUES (100,'JEAN-PIERRE','DUPONT','dupont@email.com','1234567','ACTIVE', 100);
INSERT INTO T_USER (ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, STATUS, TEAM_ID) VALUES (101,'MARIE','DUBOIS','dubois@email.com','4321','ACTIVE',102);
INSERT INTO T_USER (ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, STATUS, TEAM_ID) VALUES (102,'ANTOINE','GENES','genes@email.com','6789','ACTIVE',103);
INSERT INTO T_USER (ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, STATUS, TEAM_ID) VALUES (103,'MOHAMED','FOFANA','fofana@email.com','1234','ACTIVE',108);
INSERT INTO T_USER (ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, STATUS, TEAM_ID) VALUES (104,'SALIOU','THIAM','thiam@email.com','salt','INACTIVE',108);
INSERT INTO T_USER (ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, STATUS, TEAM_ID) VALUES (105,'SOPHIE','CARTIER','cartier@email.com','soso','ACTIVE',108);
INSERT INTO T_USER (ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, STATUS, TEAM_ID) VALUES (106,'FARAH','MOH','farah@email.com','mofa','INACTIVE',105);
*/
-- INSERT STORIES

-- INSERT SPRINTS

-- INSERT DAILYMEETINGS

-- INSERT BACKLOGS

-- INSERT ATTACHMENTS