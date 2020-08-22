package uz.azamat.demo.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.azamat.demo.model.WorkPlace;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WorkPlaceImpl implements WorkPlaceDao {
    JdbcTemplate jdbcTemplate;

    public WorkPlaceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(List<WorkPlace> workPlaces, int id) {
        String query = "insert into workplaces(name, id) values(?,?)";
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                WorkPlace workPlace = workPlaces.get(i);
                ps.setString(1, workPlace.getWorkPlaceName());
                ps.setInt(2, id);
            }

            @Override
            public int getBatchSize() {
                return workPlaces.size();
            }
        });
    }

    @Override
    public List<WorkPlace> getWorkPlacesById(int id) {
        String query = "select * from workplaces where id = " + id;
        return jdbcTemplate.query(query, new WorkPlaceRowMapper());
    }
}
