USE recruitment_agency;

INSERT INTO `recruitment_agency`.`users` (`username`, `password`, `role`) VALUES
('a1', 'a1', 'ROLE_AGENT'), 
('a2', 'a2', 'ROLE_AGENT'),
('e1', 'e1', 'ROLE_ENTERPRISE'),
('e2', 'e2', 'ROLE_ENTERPRISE'),
('c1', 'c1', 'ROLE_CANDIDATE'),
('c2', 'c2', 'ROLE_CANDIDATE');

SELECT * FROM users;

INSERT INTO `recruitment_agency`.`agents_info`(`user_id`) VALUES (1);
INSERT INTO `recruitment_agency`.`agents_info`(`user_id`) VALUES (2);

SELECT * FROM agents_info;

INSERT INTO `recruitment_agency`.`enterprises_info`(`user_id`,`name`, `email`) VALUES 
(3, 'Enterprise1', 'e1@gmail.com'), (4, 'Enterprise2', 'e2@gmail.com');

SELECT * FROM enterprises_info;

INSERT INTO `recruitment_agency`.`candidates_info`(`user_id`,`name`, `email`) VALUES
(5, 'Candidate1', 'c1@gmail.com'), (6, 'Candidate2', 'c2@gmail.com');

SELECT * FROM candidates_info;

INSERT INTO `recruitment_agency`.`applications`(`enterprise_id`,`agent_id`,`profession`,`quantity`,`agent_note`)
VALUES 
 (4,2,'first',2,'this is a first test note of second agent.'),
 (4,2,'second',2,'this is a second test note of second agent.'),
 (4,2,'third',2,'this is a third test note of second agent.');

SELECT * FROM applications;


INSERT INTO `recruitment_agency`.`applicants_for_applications` (`application_id`,`candidate_id`) 
VALUES(2, 5), (2, 6), (3, 5), (3, 6);

SELECT * FROM applicants_for_applications;
