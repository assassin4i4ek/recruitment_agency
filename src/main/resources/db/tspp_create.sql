CREATE SCHEMA IF NOT EXISTS recruitment_agency;

USE recruitment_agency;

CREATE TABLE IF NOT EXISTS users (
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
    role ENUM('ROLE_AGENT', 'ROLE_CANDIDATE', 'ROLE_ENTERPRISE') NOT NULL,
    PRIMARY KEY(id)
);
			
CREATE TABLE IF NOT EXISTS agents_info (
	user_id INT NOT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS candidates_info (
    user_id INT NOT NULL,
	email VARCHAR(320) NOT NULL,
    name VARCHAR(256),
    PRIMARY KEY (user_id),
    CONSTRAINT FK_candidate_id_user_id FOREIGN KEY (user_id)
        REFERENCES users (id)
);
   
CREATE TABLE IF NOT EXISTS enterprises_info (
    user_id INT NOT NULL,
	email VARCHAR(320) NOT NULL,
    name VARCHAR(256),
    PRIMARY KEY (user_id),
    CONSTRAINT FK_enterprise_id_user_id FOREIGN KEY (user_id)
        REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS applications (
							id INT NOT NULL AUTO_INCREMENT,
							enterprise_id INT NOT NULL,
                            agent_id INT NOT NULL,
							registration_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
							profession VARCHAR(100) NOT NULL,
							quantity INT(3) DEFAULT 1,
							agent_note TEXT,
                            agent_order INT, 
							PRIMARY KEY(id),
							CONSTRAINT FK_applications_enterprise_id FOREIGN KEY(enterprise_id) REFERENCES enterprises_info(user_id),
                            CONSTRAINT FK_applications_agent_id FOREIGN KEY(agent_id) REFERENCES agents_info(user_id));
                            
CREATE TABLE IF NOT EXISTS applicants_for_applications (
										  id INT NOT NULL AUTO_INCREMENT,
										  application_id INT NOT NULL,
                                          candidate_id INT NOT NULL,
                                          stage ENUM('INTERNAL_INVITATION',
													 'INTERNAL_FAILED',
                                                     'INTERNAL_PASSED',
                                                     'EXTERNAL_INVITED',
													 'EXTERNAL_FAILED',
                                                     'EXTERNAL_PASSED',
													 'GOT_JOB') DEFAULT 'INTERNAL_INVITATION',
                                          applicant_order INT,           
                                          PRIMARY KEY(id),
                                          CONSTRAINT FK_application_id FOREIGN KEY(application_id) REFERENCES applications(id),
                                          CONSTRAINT FK_candidate_id FOREIGN KEY(candidate_id) REFERENCES candidates_info(user_id));