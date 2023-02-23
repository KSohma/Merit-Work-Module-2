package com.techelevator.projects.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.sql.DataSource;

import com.techelevator.projects.model.Project;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Employee;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcEmployeeDao implements EmployeeDao {

	private final JdbcTemplate jdbcTemplate;

	public JdbcEmployeeDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		String sqlQuery = "SELECT * FROM employee e;";
		List<Employee> employees = new ArrayList<>();
		int i = 0;
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
			employees = Search(results);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		return employees;
	}

	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		String sqlQuery = "SELECT * " +
							"FROM employee e " +
							"WHERE upper(first_name) LIKE ? AND upper(last_name) LIKE ?;";
		List<Employee> employees = new ArrayList<>();
		int i = 0;
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, prepForSearch(firstNameSearch), prepForSearch(lastNameSearch));
			employees = Search(results);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		String sqlQuery = "SELECT * " +
				"FROM employee e " +
				"JOIN project_employee pe ON pe.employee_id = e.employee_id " +
				"JOIN project p ON p.project_id = pe.project_id " +
				"WHERE p.project_id = ?;";
		List<Employee> employees = new ArrayList<>();
		int i = 0;
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, projectId);
			employees = Search(results);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return employees;
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		String sqlQueryOne = "INSERT INTO project_employee (project_id, employee_id) " +
				"VALUES (?, ?);";
		jdbcTemplate.update(sqlQueryOne, projectId, employeeId);
	}

	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String sqlQuerySelect = "SELECT employee_id " +
				"FROM project_employee " +
				"WHERE project_id = ? AND employee_id = ?;";
		String sqlQueryDelete = "DELETE FROM project_employee " +
				"WHERE project_id = ? AND employee_id = ?;";
		if (jdbcTemplate.queryForObject(sqlQuerySelect, Long.class, projectId, employeeId) != null){
			jdbcTemplate.update(sqlQueryDelete, projectId, employeeId);
		}
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		String sqlQuery = "SELECT e.employee_id, e.department_id, e.first_name, e.last_name, e.birth_date, e.hire_date " +
				"FROM employee e " +
				"FULL JOIN project_employee pe ON pe.employee_id = e.employee_id " +
				"FULL JOIN project p ON p.project_id = pe.project_id " +
				"WHERE p.project_id IS NULL;";
		List<Employee> employees = new ArrayList<>();
		int i = 0;
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
			employees = Search(results);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return employees;
	}

	public List<Employee> Search (SqlRowSet results){
			List<Employee> employees = new ArrayList<>();
			int i = 0;

			while (results.next()){
				Employee employee = new Employee();
				employee.setId(results.getLong("employee_id"));
				employee.setFirstName(results.getString("first_name"));
				employee.setLastName(results.getString("last_name"));
				employee.setBirthDate(LocalDate.parse(Objects.requireNonNull(results.getString("birth_date"))));
				employee.setHireDate((LocalDate.parse(Objects.requireNonNull(results.getString("hire_date")))));
				if ((Long) results.getLong("department_id") != null){
					employee.setDepartmentId(results.getLong("department_id"));
				}
				employees.add(i,employee);
				i++;
			}
			return employees;
	}

	public String prepForSearch (String name){
		return "%" + name.toUpperCase(Locale.ROOT) + "%";
	}
}
