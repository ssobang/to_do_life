package haza.demo.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import haza.demo.domain.WorkList;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class JdbcListRepository implements ListRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcListRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<WorkList> listRowMapper() {
		return new RowMapper<WorkList>() {
			@Override
			public WorkList mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkList workList = new WorkList();
				workList.setWorkNo(rs.getLong("workNo"));
				workList.setDate(rs.getDate("date"));
				workList.setWork(rs.getString("work"));
				workList.setMemo(rs.getString("memo"));
				workList.setWorkyn(rs.getBoolean("workyn"));
				return workList;
			}
		};
	}

//	@Override
//	public WorkList save(WorkList workList) {
//		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//		jdbcInsert.withTableName("list").usingGeneratedKeyColumns("workNo");
//		Map<String, Object> parameters = new HashMap<>();
//		parameters.put("workNo", workList.getWorkNo());
//		parameters.put("date", workList.getDate());
//		parameters.put("work", workList.getWork());
//		parameters.put("memo", workList.getMemo());
//		return workList;
//	}
	
	@Override
	   public WorkList save(WorkList workList) {
	      SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
	      jdbcInsert.withTableName("list").usingGeneratedKeyColumns("workno");
	      Map<String, Object> parameters = new HashMap<>(); 
	      parameters.put("date", workList.getDate());
	      parameters.put("work", workList.getWork());
	      parameters.put("memo", workList.getMemo());
	      parameters.put("workyn", workList.getWorkyn());
	      Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
	      workList.setWorkNo(key.longValue());
	      return workList;
	   }

	@Override
	public Optional<WorkList> findByNo(Long workNo) {
		log.info("리파지토리실행됨1");
		String sql = "SELECT * FROM LIST WHERE WORKNO = ?";
		List<WorkList> result = jdbcTemplate.query(sql, listRowMapper(), workNo);
		log.info("리파지토리끝남");
		return result.stream().findAny();
	}
	
	public List<WorkList> today() {
		log.info("today 리파지토리 실행");
		String sql = "SELECT * FROM LIST WHERE DATE = CURRENT_DATE()";
		List<WorkList> result = jdbcTemplate.query(sql, listRowMapper());
		log.info("길이", result.size());
		log.info("today 리파지토리 종료");
		return result;
	}
	
	public List<WorkList> findByDate(String date) {
		log.info("today 리파지토리 실행");
		String sql = "SELECT * FROM LIST WHERE DATE = ?";
		List<WorkList> result = jdbcTemplate.query(sql, listRowMapper(), date);
		log.info("길이 : {}", result.size());
		log.info("today 리파지토리 종료");
		return result;
	}

	@Override
	public WorkList update(Long workId, WorkList workList) {
		String sql = "UPDATE LIST SET DATE = ?, WORK = ?, MEMO = ?, WORKYN = ? WHERE WORKNO = ?";
		jdbcTemplate.update(sql, workList.getDate(), workList.getWork(), workList.getMemo(), workList.getWorkyn(), workId);
		workList.setWorkNo(workId);
		return findByNo(workId).get();
	}

	@Override
	public void deleteWork(Long workNo) {
		log.info("delete 리파지토리 실행됨");
		String sql = "delete from list where workno = ?";
		jdbcTemplate.update(sql, workNo);
		log.info("delete 리파지토리 끝남");
	}

	@Override
	public List<WorkList> findAll() {
		log.info("리파지토리 실행됨");
		return jdbcTemplate.query("SELECT * FROM LIST", listRowMapper());
	}

	@Override
	public List<WorkList> search (String work) {
		log.info("search리파지토리 실행됨");
		log.info("{}", work);
		String sql = "SELECT * FROM LIST WHERE WORK LIKE ?";
		String search = "%" + work + "%";
		log.info("search리파지토리 끝");
		return jdbcTemplate.query(sql, listRowMapper(), search);
	}
	

	public List<WorkList> find() {
		log.info("리파지토리 실행됨");
		return jdbcTemplate.query("SELECT * FROM LIST", listRowMapper());
	}

	@Override
	public List<WorkList> manageTrue() {
		log.info("manageTrue 리파지토리 실행");
		List<WorkList> workTrue = jdbcTemplate.query("SELECT * FROM LIST WHERE WORKYN = TRUE" , listRowMapper());
		return workTrue;
	}
	
	@Override
	public List<WorkList> manageFalse() {
		log.info("manageTrue 리파지토리 실행");
		List<WorkList> workFalse = jdbcTemplate.query("SELECT * FROM LIST WHERE WORKYN = FALSE" , listRowMapper());
		return workFalse;
	}


	
}
