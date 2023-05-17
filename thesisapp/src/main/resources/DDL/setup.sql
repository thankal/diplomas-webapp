-- Database: thesisdb

DROP DATABASE IF EXISTS thesisdb;

CREATE DATABASE thesisdb
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    -- LC_COLLATE = 'en_US.UTF-8'
    -- LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

GRANT TEMPORARY, CONNECT ON DATABASE thesisdb TO PUBLIC;

GRANT ALL ON DATABASE thesisdb TO postgres;

\c thesisdb

-- Table: public.users

DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    user_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(500) COLLATE pg_catalog."default" NOT NULL,
    email character varying(200) COLLATE pg_catalog."default" NOT NULL,
    role character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_id PRIMARY KEY (user_id),
    CONSTRAINT email UNIQUE (email),
    CONSTRAINT username UNIQUE (username)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

-- Table: public.professors

-- DROP TABLE IF EXISTS public.professors;

CREATE TABLE IF NOT EXISTS public.professors
(
    prof_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    first_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    speciality character varying(300) COLLATE pg_catalog."default" NOT NULL,
    user_fk bigint NOT NULL,
    CONSTRAINT professors_pkey PRIMARY KEY (prof_id),
    CONSTRAINT professors_user_fk_fkey FOREIGN KEY (user_fk)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.professors
    OWNER to postgres;



-- Table: public.students

-- DROP TABLE IF EXISTS public.students;

CREATE TABLE IF NOT EXISTS public.students
(
    student_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    first_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    year_of_studies integer NOT NULL,
    average_grade numeric(2,0) NOT NULL,
    remaining_courses integer NOT NULL,
    user_fk bigint NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id),
    CONSTRAINT user_fk FOREIGN KEY (user_fk)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.students
    OWNER to postgres;

-- Table: public.thesis

-- DROP TABLE IF EXISTS public.thesis;

CREATE TABLE IF NOT EXISTS public.thesis
(
    thesis_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    title character varying(500) COLLATE pg_catalog."default" NOT NULL,
    objectives character varying(1000) COLLATE pg_catalog."default" NOT NULL,
    prof_fk bigint NOT NULL,
    CONSTRAINT thesis_pkey PRIMARY KEY (thesis_id),
    CONSTRAINT thesis_teacher_fk_fkey FOREIGN KEY (prof_fk)
        REFERENCES public.professors (prof_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.thesis
    OWNER to postgres;

-- Table: public.applications

-- DROP TABLE IF EXISTS public.applications;

CREATE TABLE IF NOT EXISTS public.applications
(
    application_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    student_fk bigint NOT NULL,
    thesis_fk bigint NOT NULL,
    CONSTRAINT applications_pkey PRIMARY KEY (application_id),
    CONSTRAINT applications_student_fk_fkey FOREIGN KEY (student_fk)
        REFERENCES public.students (student_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT applications_thesis_fk_fkey FOREIGN KEY (thesis_fk)
        REFERENCES public.thesis (thesis_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.applications
    OWNER to postgres;


-- Table: public.assignments

-- DROP TABLE IF EXISTS public.assignments;

CREATE TABLE IF NOT EXISTS public.assignments
(
    assignment_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    thesis_fk bigint NOT NULL,
    student_fk bigint NOT NULL,
    CONSTRAINT assignments_pkey PRIMARY KEY (assignment_id),
    CONSTRAINT assignments_student_fk_fkey FOREIGN KEY (student_fk)
        REFERENCES public.students (student_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT assignments_thesis_fk_fkey FOREIGN KEY (thesis_fk)
        REFERENCES public.thesis (thesis_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.assignments
    OWNER to postgres;


-- Table: public.grades

-- DROP TABLE IF EXISTS public.grades;

CREATE TABLE IF NOT EXISTS public.grades
(
    eval_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    implementation double precision,
    report double precision,
    presentation double precision,
    total double precision,
    assignment_fk bigint NOT NULL,
    CONSTRAINT evaluations_pkey PRIMARY KEY (eval_id),
    CONSTRAINT grades_assignment_fk_fkey FOREIGN KEY (assignment_fk)
        REFERENCES public.assignments (assignment_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.grades
    OWNER to postgres;