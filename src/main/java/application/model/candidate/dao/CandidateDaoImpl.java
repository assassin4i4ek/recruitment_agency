package application.model.candidate.dao;

import application.model.application.Application;
import application.model.application.EmploymentType;
import application.model.candidate.Applicant;
import application.model.candidate.ApplicantStage;
import application.model.candidate.Candidate;
import application.model.candidate.CandidateRegistrationForm;
import application.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDaoImpl implements CandidateDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Candidate findCandidateById(int candidateId) {
        String sql = "SELECT user_id, email, name, profession, employment_type, required_salary_cu_per_month," +
                " experience, skills FROM candidates_info WHERE user_id=" + candidateId;
        List<Candidate> candidates = jdbcTemplate.query(sql, new CandidateMapper());
        return candidates != null ? candidates.get(0) : null;
    }

    @Override
    public List<Applicant> findApplicantsForApplication(Application application) {
        String  sql = "SELECT user_id, email, name, profession, employment_type, required_salary_cu_per_month, experience," +
                " skills, stage FROM candidates_info" +
                " INNER JOIN applicants_for_applications ON candidates_info.user_id=applicants_for_applications.candidate_id" +
                " WHERE applicants_for_applications.application_id=" + application.getId() +
                " ORDER BY applicant_order";
        List<Applicant> applicants = jdbcTemplate.query(sql, new ApplicantMapper());
        return applicants != null ? applicants : new ArrayList<>(0);
    }

    @Override
    public void reorderApplicantsOfApplication(Application application) {
        List<Applicant> applicants = application.getApplicants();
        for (int i = 0; i < applicants.size(); ++i) {
            String sql = "UPDATE applicants_for_applications SET applicant_order=" + i
                    + " WHERE application_id=" + application.getId() +
                    " AND candidate_id=" + applicants.get(i).getId();
            jdbcTemplate.update(sql);
        }
    }

    @Override
    public void updateApplicantOfApplicationStage(Application application, Applicant applicant) {
        String sql = "UPDATE applicants_for_applications SET stage='" +
                applicant.getApplicantStage().name() + "' WHERE application_id=" + application.getId() +
                " AND candidate_id=" + applicant.getId();
        jdbcTemplate.update(sql);
    }

    @Override
    public void deleteApplicantsForApplication(Application application) {
        String sql = "DELETE FROM applicants_for_applications WHERE application_id=" + application.getId();
        jdbcTemplate.update(sql);
    }

    @Override
    public void updateCandidateInfo(Candidate candidate) {
        String sql = "UPDATE candidates_info SET name=?, email=?, profession=?, employment_type=?, required_salary_cu_per_month=?," +
                " experience=?, skills=? WHERE user_id=?";
        jdbcTemplate.update(sql, candidate.getName(), candidate.getEmail(), candidate.getProfession(),
                candidate.getEmploymentType().name(), candidate.getRequiredSalaryCuPerMonth(),
                candidate.getExperience(), candidate.getSkills(), candidate.getId());
    }

    @Override
    public void createNewCandidate(User user, CandidateRegistrationForm form) {
        String sql = "INSERT INTO candidates_info(user_id,email,name,profession,employment_type,required_salary_cu_per_month," +
                "experience,skills) VALUE (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getId(), form.getEmail(), form.getName(), form.getProfession().isEmpty() ? null : form.getProfession(),
                form.getEmploymentType(), form.getRequiredSalaryCuPerMonth(), form.getExperience(), form.getSkills());
    }

    private class CandidateMapper implements RowMapper<Candidate> {
        @Override
        public Candidate mapRow(ResultSet resultSet, int i) throws SQLException {
            Candidate candidate = new Candidate();
            candidate.setId(resultSet.getInt("user_id"));
            candidate.setName(resultSet.getString("name"));
            candidate.setEmail(resultSet.getString("email"));
            candidate.setProfession(resultSet.getString("profession"));
            candidate.setEmploymentType(EmploymentType.valueOf(resultSet.getString("employment_type")));
            candidate.setRequiredSalaryCuPerMonth(resultSet.getInt("required_salary_cu_per_month"));
            Blob experienceBlob = resultSet.getBlob("experience");
            if (experienceBlob != null) {
                BufferedReader agentNoteReader = new BufferedReader(new InputStreamReader(
                        experienceBlob.getBinaryStream()));

                StringBuilder stringBuilder = new StringBuilder();
                try {
                    String line = agentNoteReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line);
                        line = agentNoteReader.readLine();
                    }
                } catch (IOException e) {
                    throw new SQLException("Error reading notes");
                }
                candidate.setExperience(stringBuilder.toString());
            }
            Blob skillsBlob = resultSet.getBlob("skills");
            if (skillsBlob != null) {
                BufferedReader agentNoteReader = new BufferedReader(new InputStreamReader(
                        skillsBlob.getBinaryStream()));

                StringBuilder stringBuilder = new StringBuilder();
                try {
                    String line = agentNoteReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line);
                        line = agentNoteReader.readLine();
                    }
                } catch (IOException e) {
                    throw new SQLException("Error reading notes");
                }
                candidate.setSkills(stringBuilder.toString());
            }
            return candidate;
        }
    }

    private class ApplicantMapper implements RowMapper<Applicant> {
        @Override
        public Applicant mapRow(ResultSet resultSet, int i) throws SQLException {
            Applicant applicant = new Applicant();
            applicant.setId(resultSet.getInt("user_id"));
            applicant.setName(resultSet.getString("name"));
            applicant.setEmail(resultSet.getString("email"));
            applicant.setProfession(resultSet.getString("profession"));
            applicant.setEmploymentType(EmploymentType.valueOf(resultSet.getString("employment_type")));
            applicant.setRequiredSalaryCuPerMonth(resultSet.getInt("required_salary_cu_per_month"));
            Blob experienceBlob = resultSet.getBlob("experience");
            if (experienceBlob != null) {
                BufferedReader agentNoteReader = new BufferedReader(new InputStreamReader(
                        experienceBlob.getBinaryStream()));

                StringBuilder stringBuilder = new StringBuilder();
                try {
                    String line = agentNoteReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line);
                        line = agentNoteReader.readLine();
                    }
                } catch (IOException e) {
                    throw new SQLException("Error reading notes");
                }
                applicant.setExperience(stringBuilder.toString());
            }
            Blob skillsBlob = resultSet.getBlob("skills");
            if (skillsBlob != null) {
                BufferedReader agentNoteReader = new BufferedReader(new InputStreamReader(
                        skillsBlob.getBinaryStream()));

                StringBuilder stringBuilder = new StringBuilder();
                try {
                    String line = agentNoteReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line);
                        line = agentNoteReader.readLine();
                    }
                } catch (IOException e) {
                    throw new SQLException("Error reading notes");
                }
                applicant.setSkills(stringBuilder.toString());
            }
            applicant.setApplicantStage(ApplicantStage.valueOf(resultSet.getString("stage")));
            return applicant;
        }
    }
}
