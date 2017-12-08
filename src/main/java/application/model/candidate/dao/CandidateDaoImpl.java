package application.model.candidate.dao;

import application.model.application.Application;
import application.model.candidate.Applicant;
import application.model.candidate.ApplicantStage;
import application.model.candidate.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        String sql = "SELECT user_id, name, email FROM candidates_info WHERE user_id=" + candidateId;
        List<Candidate> candidates = jdbcTemplate.query(sql, new CandidateMapper());
        return candidates != null ? candidates.get(0) : null;
    }

    @Override
    public List<Applicant> findApplicantsForApplication(Application application) {
        String  sql = "SELECT user_id, name, email, stage FROM candidates_info" +
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

    private class CandidateMapper implements RowMapper<Candidate> {
        @Override
        public Candidate mapRow(ResultSet resultSet, int i) throws SQLException {
            Candidate candidate = new Candidate();
            candidate.setId(resultSet.getInt("user_id"));
            candidate.setName(resultSet.getString("name"));
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
            applicant.setApplicantStage(ApplicantStage.valueOf(resultSet.getString("stage")));
            return applicant;
        }
    }
}
