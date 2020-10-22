package no.kristiania.db;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectDaoTest {
    @Test
    void shouldRetrieveSavedProject() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:testdatabase;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();

        ProjectDao projectDao = new ProjectDao(dataSource);

        Project projectSample = sampleProject();
        projectDao.insert(projectSample);

        Project retrievedSample = projectDao.retrieve(projectSample.getId());

        assertThat(projectSample).isNotEqualTo(retrievedSample);
        assertThat(projectSample.getName()).isEqualTo(retrievedSample.getName());
        assertThat(projectSample.getDepartment()).isEqualTo(retrievedSample.getDepartment());
    }

    @Test
    void ShouldRetrieveSingleProject() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:testdatabase;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();

        ProjectDao projectDao = new ProjectDao(dataSource);

        Project sampleProject1 = sampleProject();
        Project sampleProject2 = sampleProject();
        Project sampleProject3 = sampleProject();

        List<Project> projectList = new ArrayList<>();
        projectList.add(sampleProject1);
        projectList.add(sampleProject2);
        projectList.add(sampleProject3);

        for(Project p : projectList) {
            projectDao.insert(p);
        }

        List<Project> retrievedProjectList = projectDao.list();

        for (int i = 0; i < 3; i++) {
            assertThat(projectList.get(i)).isNotEqualTo(retrievedProjectList.get(i));
            assertThat(projectList.get(i).getName()).isEqualTo(retrievedProjectList.get(i).getName());
            assertThat(projectList.get(i).getDepartment()).isEqualTo(retrievedProjectList.get(i).getDepartment());
        }
    }

    private Project sampleProject() {
        String projectName = sampleProjectName();
        String projectDepartment = sampleProjectDepartment();
        return new Project(projectName, projectDepartment);

    }

    private String sampleProjectName() {
        String[] options = {"Build final feature", "Refurnish office", "Marked main product", "Sell assets"};
        Random random = new Random();
        return options[random.nextInt(options.length)];
    }

    private String sampleProjectDepartment() {
        String[] options = {"HR", "IT", "Management", "PR"};
        Random random = new Random();
        return options[random.nextInt(options.length)];
    }
}
