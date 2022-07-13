package model;

public class Customer {
	private int customerId;
	private String customerUsername;
	private String customerPassword;
	private String customerForename;
	private String customerSurname;
	private String customerAddress;
	private String customerTelephone;
	private String customerEmail;
	private String customerPaymentDetails;
	
	/**
	 * Empty constructor for Customer class
	 */
	public Customer() {
		
	}
	/**
	 * This is a user-facing constructor which allows the user to enter information which can be used
	 * to create a database entry for that user
	 * @param customerUsername
	 * @param customerPassword
	 * @param customerForename
	 * @param customerSurname
	 * @param customerAddress
	 * @param customerTelephone
	 * @param customerEmail
	 * @param customerPaymentDetails
	 */
	public Customer(String customerUsername, String customerPassword, String customerForename,String customerSurname, String customerAddress,String customerTelephone,String customerEmail,String customerPaymentDetails) {
		this.customerUsername = customerUsername;
		this.customerPassword = customerPassword;
		this.customerForename = customerForename;
		this.customerSurname = customerSurname;
		this.customerAddress = customerAddress;
		this.customerTelephone = customerTelephone;
		this.customerEmail = customerEmail;
		this.customerPaymentDetails = customerPaymentDetails;
	}
	/**
	 * This is a database facing constructor which can create java Customer objects from the Customer table
	 * in the database
	 * @param customerId
	 * @param customerUsername
	 * @param customerPassword
	 * @param customerForename
	 * @param customerSurname
	 * @param customerAddress
	 * @param customerTelephone
	 * @param customerEmail
	 * @param customerPaymentDetails
	 */
	public Customer(int customerId, String customerUsername, String customerPassword, String customerForename, String customerSurname, String customerAddress,String customerTelephone,String customerEmail,String customerPaymentDetails) {
		this.customerUsername = customerUsername;
		this.customerPassword = customerPassword;
		this.customerId = customerId;
		this.customerForename = customerForename;
		this.customerSurname = customerSurname;
		this.customerAddress = customerAddress;
		this.customerTelephone = customerTelephone;
		this.customerEmail = customerEmail;
		this.customerPaymentDetails = customerPaymentDetails;
	}
	
	/**
	 * Prints customer details to the console
	 */
	public void printCustomerDetails() {
		System.out.println("Customer Username: " + getCustomerUsername());
		System.out.println("Customer Id: " + getCustomerId());
		System.out.println("Customer Forename: " + getCustomerForename());
		System.out.println("Customer Surname: " + getCustomerSurname());
		System.out.println("Customer Address: " + getCustomerAddress());
		System.out.println("Customer Telephone: " + getCustomerTelephone());
		System.out.println("Customer Email: " + getCustomerEmail());
		System.out.println("Customer PaymentDetails: " + getCustomerPaymentDetails());
	}
	
	/**
	 * Prints basic customer details to the console
	 * @param index
	 */
	public void printBasicCustomerDetails(int index) {
		index += 1;
		System.out.println("" + index + ". " + getCustomerUsername() + ", " + getCustomerId() + ", " + getCustomerForename() + ", " + getCustomerSurname() + ", " + getCustomerAddress() + ", " + getCustomerTelephone());
	}
	
	/**
	 * Returns customerId
	 * @return
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * Sets customerId
	 * @return
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	/**
	 * Returns customerForename
	 * @return
	 */
	public String getCustomerForename() {
		return customerForename;
	}

	/**
	 * Sets customerForename
	 * @return
	 */
	public void setCustomerForename(String customerForename) {
		this.customerForename = customerForename;
	}
	
	/**
	 * Returns customerSurname
	 * @return
	 */
	public String getCustomerSurname() {
		return customerSurname;
	}

	/**
	 * Sets customerSurname
	 * @return
	 */
	public void setCustomerSurname(String customerSurname) {
		this.customerSurname = customerSurname;
	}

	/**
	 * Returns customerAddress
	 * @return
	 */
	public String getCustomerAddress() {
		return customerAddress;
	}

	/**
	 * Sets customerAddress
	 * @return
	 */
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	/**
	 * Returns customerTelephone
	 * @return
	 */
	public String getCustomerTelephone() {
		return customerTelephone;
	}

	/**
	 * Sets customerTelephone
	 * @return
	 */
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}

	/**
	 * Returns customerEmail
	 * @return
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * Sets customerEmail
	 * @return
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * Returns customerPaymentDetails
	 * @return
	 */
	public String getCustomerPaymentDetails() {
		return customerPaymentDetails;
	}

	/**
	 * Sets customerPaymentDetails
	 * @return
	 */
	public void setCustomerPaymentDetails(String customerPaymentDetails) {
		this.customerPaymentDetails = customerPaymentDetails;
	}
	
	/**
	 * Returns customerUsername
	 * @return
	 */
	public String getCustomerUsername() {
		return customerUsername;
	}
	
	/**
	 * Sets customerUsername
	 * @return
	 */
	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}
	
	/**
	 * Returns customerPassword
	 * @return
	 */
	public String getCustomerPassword() {
		return customerPassword;
	}
	
	/**
	 * Sets customerPassword
	 * @return
	 */
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	
}
