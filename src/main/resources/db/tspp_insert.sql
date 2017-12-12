USE recruitment_agency;

INSERT INTO spheres VALUES
('Information and communication'), 
('Jurisprudence');

INSERT INTO professions_and_spheres
(`profession`,`sphere`) VALUES
('System administrator','Information and communication'),
('Database administrator','Information and communication'),
('Software developer', 'Information and communication'),
('Quality assurance', 'Information and communication'),
('Lawyer','Jurisprudence'),
('Secretary General','Jurisprudence'),
('Detective','Jurisprudence'),
('Policeman','Jurisprudence'),
('Notary','Jurisprudence');

INSERT INTO `recruitment_agency`.`users` (`username`, `password`, `role`) VALUES
('a1', 'a1', 'ROLE_AGENT'), 
('a2', 'a2', 'ROLE_AGENT'),
('e1', 'e1', 'ROLE_ENTERPRISE'),
('e2', 'e2', 'ROLE_ENTERPRISE'),
('c1', 'c1', 'ROLE_CANDIDATE'),
('c2', 'c2', 'ROLE_CANDIDATE');

SELECT * FROM users;

INSERT INTO `recruitment_agency`.`agents_info`(`user_id`) VALUES (1),(2);

SELECT * FROM agents_info;

INSERT INTO `recruitment_agency`.`enterprises_info`(`user_id`,`name`, `email`, `contact_person_name`) VALUES 
(3, 'Enterprise1', 'e1@gmail.com', 'Микита Кропива'), (4, 'Enterprise2', 'e2@gmail.com', 'Тарас Тарченко');

SELECT * FROM enterprises_info;

INSERT INTO `recruitment_agency`.`candidates_info`
(`user_id`,`email`,`name`,`profession`,`employment_type`,`required_salary_cu_per_month`,`experience`,`skills`) VALUES
(5, 'c1@gmail.com', 'Candidate1', 'Lawyer','FULL_TIME', 4000, 'lawyer, notary, secretary', 'i don\'t know'),
(6, 'c2@gmail.com', 'Candidate2', null, 'NOT_IMPORTANT', 2000, 'policeman, detective', 'criminal, military, shooting...');

SELECT * FROM candidates_info;

INSERT INTO `recruitment_agency`.`applications`
(`enterprise_id`,`agent_id`,`profession`,`quantity`,`employment_type`,`salary_cu_per_months`,
`demanded_skills`,`agent_note`)
VALUES
 (4,2,'Lawyer',2, 'FULL_TIME',4000,'criminal law','this is a first test note of second agent.'),
 (4,2,'Software developer',2,'FULL_TIME', 10000, 'MySQL, Java','this is a second test note of second agent.'),
 (4,2,'Notary',2,'FULL_TIME',3000,'civil law','this is a third test note of second agent.');

SELECT * FROM applications;

INSERT INTO `recruitment_agency`.`applicants_for_applications` (`application_id`,`candidate_id`) 
VALUES(2, 5), (2, 6), (3, 5);/*, (3, 6);*/

SELECT * FROM applicants_for_applications;

SELECT * FROM agents_skills;

SELECT 
    user_id, email, name, profession, employment_type, required_salary_cu_per_month, experience, skills
FROM
    candidates_info
        LEFT JOIN
    applicants_for_applications ON application_id = 1 AND applicants_for_applications.candidate_id = candidates_info.user_id
    WHERE applicants_for_applications.candidate_id IS NULL;


