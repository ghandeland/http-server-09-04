package no.kristiania.db;

public class Project {


    private String name;
    private String department;
    private int id;

    public Project(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public Project(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
