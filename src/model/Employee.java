package model;

public class Employee {
	private int employeeId;
	private String employeeUsername;
	private String employeePassword;
	public Employee(){
		Employee employee = new Employee();
	}
	//user facing constructor
	public Employee(String employeeUsername, String employeePassword) {
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
    }
	//database facing constructor
	public Employee(int employeeId, String employeeUsername, String employeePassword) {
        this.employeeId = employeeId;
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
    }
	public void printEmployeeDetails() {
        System.out.println("Employee Id: " + getEmployeeId());
        System.out.println("Employee Username: " + getEmployeeUsername());
    }
    public void printBasicEmployeeDetails(int index) {
        index += 1;
        System.out.println("" + index + ". Username: " + getEmployeeUsername() + ", Id: " + getEmployeeId());
    }
	public int getEmployeeId() {
		return employeeId;
	}
	public String getEmployeeUsername() {
        return employeeUsername;
	}
    public String getEmployeePassword() {
        return employeePassword;
    }
	public void setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
	}
	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}
}
