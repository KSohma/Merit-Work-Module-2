package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;

public class JdbcDepartmentDao implements DepartmentDao {
	
	private final JdbcTemplate jdbcTemplate;

	public JdbcDepartmentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Department getDepartment(Long id) {
		String sqlQuery = "SELECT name FROM department WHERE department_id = ?;";
		try {
			return new Department(id, jdbcTemplate.queryForObject(sqlQuery, String.class, id));
		} catch (EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public List<Department> getAllDepartments() {
		String sqlQuery = "SELECT department_id, name FROM department;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		List<Department> departments = new ArrayList<>();
		int i = 0;
		Department department;
		while (results.next()){
			department = new Department(results.getLong("department_id"), results.getString("name"));
			departments.add(i,department);
			i++;
		}
		return departments;
	}

	@Override
	public void updateDepartment(Department updatedDepartment) {
		long id = updatedDepartment.getId();
		String name = updatedDepartment.getName();
		String sqlQuery = "UPDATE department SET name = ? WHERE department_id = ?;";
		jdbcTemplate.update(sqlQuery, name, id);
	}

}
