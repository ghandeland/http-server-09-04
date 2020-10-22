package no.kristiania.db;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao {

    private DataSource dataSource;
    private List<Project> projects = new ArrayList<>();

    public ProjectDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Project project) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("insert into project (name, department) values(?, ?)", Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, project.getName());
                statement.setString(2, project.getDepartment());

                statement.execute();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        project.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public List<Project> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from project")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while(resultSet.next()) {


                        List<Project> projects = new ArrayList<>();

                        while(resultSet.next()) {

                            String name = resultSet.getString("name");
                            String department = resultSet.getString("department");

                            projects.add(new Project(name, department));
                        }

                        return projects;
                    }
                }
            }
        }
        return null;
    }

    public Project retrieve(int id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from project where id = ?")) {

                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if(resultSet.next()) {
                        String name = resultSet.getString("name");
                        String department = resultSet.getString("department");

                        return new Project(id, name, department);
                    }
                }
            }
        }
        System.out.println("Project not found");
        return null;
    }
}
