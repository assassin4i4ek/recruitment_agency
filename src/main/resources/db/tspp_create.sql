CREATE SCHEMA IF NOT EXISTS recruitment_agency;

USE recruitment_agency;


CREATE TABLE IF NOT EXISTS spheres(
                    name VARCHAR(100) NOT NULL,
                    PRIMARY KEY(name)
);

CREATE TABLE IF NOT EXISTS professions_and_spheres (
                                    profession VARCHAR(100) NOT NULL,
                                    sphere VARCHAR(100) NOT NULL,
                                    PRIMARY KEY(profession),
                                    FOREIGN KEY(sphere) REFERENCES spheres(name));           


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
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS candidates_info (
    user_id INT NOT NULL,
	email VARCHAR(320) NOT NULL,
    name VARCHAR(256),
    
    profession VARCHAR(256),
    employment_type ENUM('NOT_IMPORTANT', 'FULL_TIME', 'PART_TIME') NOT NULL DEFAULT 'NOT_IMPORTANT',
	required_salary_cu_per_month INT,
    experience TEXT,
    skills TEXT,

    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (profession) REFERENCES professions_and_spheres(profession)
);
   
CREATE TABLE IF NOT EXISTS enterprises_info (
    user_id INT NOT NULL,
    name VARCHAR(256),
	email VARCHAR(320) NOT NULL,
    
    contact_person_name VARCHAR(100),
    
    PRIMARY KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS applications (
							id INT NOT NULL AUTO_INCREMENT,
							enterprise_id INT NOT NULL,
                            agent_id INT,
							registration_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            
                            profession VARCHAR(100) NOT NULL, /*сопостовлять с опытом кандидата*/
							quantity INT(3) DEFAULT 1,
                            employment_type ENUM('FULL_TIME', 'PART_TIME') NOT NULL, /*сопостовлять с указанной занятостью кандидата*/
                            salary_cu_per_months INT, /*сопостовлять с желаемой зарплатой кандидата?*/
                            demanded_skills TEXT, /*сопостовлять с указанными навыками кандидата*/
                            
							agent_note TEXT,
                            agent_order INT,
                            agent_collapsed BOOLEAN DEFAULT FALSE,
                            agent_collapsed_applicants BOOLEAN DEFAULT FALSE,
                            enterprise_order INT,
                            enterprise_collapsed BOOLEAN DEFAULT FALSE,
							PRIMARY KEY(id),
							FOREIGN KEY(enterprise_id) REFERENCES enterprises_info(user_id),
                            FOREIGN KEY(agent_id) REFERENCES agents_info(user_id),
                            FOREIGN KEY(profession) REFERENCES professions_and_spheres(profession));
                            
CREATE TABLE IF NOT EXISTS applicants_for_applications (
										  application_id INT NOT NULL,
                                          candidate_id INT NOT NULL,
                                          stage ENUM('INTERNAL_INVITED',
													 'INTERNAL_FAILED',
                                                     'INTERNAL_PASSED',
                                                     'EXTERNAL_INVITED',
													 'EXTERNAL_FAILED',
                                                     'EXTERNAL_PASSED',
													 'GOT_JOB') DEFAULT 'INTERNAL_INVITED',
                                          applicant_order INT,           
                                          PRIMARY KEY(application_id,candidate_id),
                                          FOREIGN KEY(application_id) REFERENCES applications(id),
                                          FOREIGN KEY(candidate_id) REFERENCES candidates_info(user_id));
			
CREATE TABLE IF NOT EXISTS agents_skills (
				id INT NOT NULL AUTO_INCREMENT,
                agent_id INT NOT NULL,
                sphere VARCHAR(100) NOT NULL,
                level INT NOT NULL,
                PRIMARY KEY(id),
                FOREIGN KEY(agent_id) REFERENCES agents_info(user_id),
                FOREIGN KEY(sphere) REFERENCES spheres(name));          
     
     
DELIMITER $$
CREATE TRIGGER init_agent_skills AFTER INSERT ON agents_info FOR EACH ROW
BEGIN
	DECLARE var_sphere VARCHAR(100);
	DECLARE stop_loop INT DEFAULT 0;
    DECLARE sphere_cursor CURSOR FOR SELECT name FROM spheres;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stop_loop = 1; 
    
    OPEN sphere_cursor;
    loop_spheres: LOOP
		FETCH sphere_cursor INTO var_sphere;

		IF stop_loop THEN
			LEAVE loop_spheres;
        END IF;
        
		INSERT INTO agents_skills(agent_id, sphere, level) VALUE (NEW.user_id, var_sphere, 0);
    END LOOP;
    CLOSE sphere_cursor;
END$$                

CREATE TRIGGER init_new_sphere AFTER INSERT ON spheres FOR EACH ROW
BEGIN
	DECLARE agent_id_var INT;
	DECLARE stop_loop INT DEFAULT 0;
    DECLARE agents_cursor CURSOR FOR SELECT user_id FROM agents_info;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET stop_loop = 1; 
    
    OPEN agents_cursor;
    loop_spheres: LOOP
		FETCH agents_cursor INTO agent_id_var;

		IF stop_loop THEN
			LEAVE loop_spheres;
        END IF;
        
		INSERT INTO agents_skills(agent_id, sphere, level) VALUE (agent_id_var, NEW.name, 0);
    END LOOP;
    CLOSE agents_cursor;
END$$      