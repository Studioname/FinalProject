package model;

public class Employee {
	private int employeeId;
	private String employeeUsername;
	private String employeePassword;
	/**
	 * Empty constructor for Employee class
	 */
	public Employee(){
	}
	
	/**
	 * User facing constructor for Employee class, allows us to create database entries in the Employee
	 * table
	 * @param employeeUsername
	 * @param employeePassword
	 */
	public Employee(String employeeUsername, String employeePassword) {
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
    }
	/**
	 * Database facing constructor for Employee class which we can use to make Employee objects from
	 * Employee table
	 * @param employeeId
	 * @param employeeUsername
	 * @param employeePassword
	 */
	public Employee(int employeeId, String employeeUsername, String employeePassword) {
        this.employeeId = employeeId;
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
    }
	
	/**
	 * Prints employee details to the console
	 */
	public void printEmployeeDetails() {
        System.out.println("Employee Id: " + getEmployeeId());
        System.out.println("Employee Username: " + getEmployeeUsername());
    }
	/**
	 * Prints basic employee details to the console
	 * @param index
	 */
    public void printBasicEmployeeDetails(int index) {
        index += 1;
        System.out.println("" + index + ". Username: " + getEmployeeUsername() + ", Id: " + getEmployeeId());
    }
    /**
     * Returns employeeID
     * @return
     */
	public int getEmployeeId() {
		return employeeId;
	}
	/**
	 * Returns employeeUsername
	 * @return
	 */
	public String getEmployeeUsername() {
        return employeeUsername;
	}
	
	/**
	 * Returns employeePassword
	 * @return
	 */
    public String getEmployeePassword() {
        return employeePassword;
    }
    
    /**
     * Sets employeeUsername
     * @param employeeUsername
     */
	public void setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
	}
	
	/**
	 * Sets employeePassword
	 * @param employeePassword
	 */
	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}
}
