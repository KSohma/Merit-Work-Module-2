package com.techelevator.projects.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Project;

public class JdbcProjectDao implements ProjectDao {

	private final JdbcTemplate jdbcTemplate;

	public JdbcProjectDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Project getProject(Long projectId) {
		String sqlQuery = "SELECT project_id, name, from_date, to_date FROM project WHERE project_id = ? ;";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, projectId);
			if(results.next()) {
				return projectMapper(results);
			}
			return null;
		} catch (EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public List<Project> getAllProjects() {
		String sqlQuery = "SELECT * FROM project;";
		List<Project> projects = new ArrayList<>();
		int i = 0;

		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
			while (results.next()){
				projects.add(i,projectMapper(results));
				i++;
			}
			return projects;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Project createProject(Project newProject) {
		String name = newProject.getName();
		LocalDate fromDate = newProject.getFromDate();
		LocalDate toDate = newProject.getToDate();

		String sqlQueryCPInsert = "INSERT INTO project (name, from_date, to_date) " +
							"VALUES (?,?,?);";
		String sqlQueryCPSelect = "SELECT project_id FROM project WHERE name = ? AND from_date = ? AND to_date = ?;";

		jdbcTemplate.update(sqlQueryCPInsert, name, fromDate, toDate);
		newProject.setId(jdbcTemplate.queryForObject(sqlQueryCPSelect, Long.class, name, fromDate, toDate));

		return newProject;
	}

	@Override
	public void deleteProject(Long projectId) {
//		String sqlQueryDpSelect = "SELECT name FROM project p " +
//									"JOIN project_employee pe ON pe.project_id = p.project_id " +
//									"WHERE p.project_id = ?";

		String sqlQueryDpDeleteOne = "DELETE FROM project WHERE project_id = ?;";
		String sqlQueryDpDeleteTwo = "DELETE FROM project_employee WHERE project_id = ?;";

		jdbcTemplate.update(sqlQueryDpDeleteTwo, projectId);
		jdbcTemplate.update(sqlQueryDpDeleteOne, projectId);
//		if (jdbcTemplate.queryForObject(sqlQueryDPSelect, Project.class, projectId) != null){
//			jdbcTemplate.update(sqlQueryDPDelete, projectId);
//		}

	}

	public Project projectMapper (SqlRowSet results){
		    Long id = results.getLong("project_id");
			String name = results.getString("name");
			LocalDate fromDate = null;
			LocalDate toDate = null;

			if (results.getString("from_date") != null){
				fromDate = LocalDate.parse(Objects.requireNonNull(results.getString("from_date")));
			}
			if (results.getString("to_date") != null) {
				toDate = LocalDate.parse(Objects.requireNonNull(results.getString("to_date")));
			}
			return new Project(id, name, fromDate, toDate);
	}

}
