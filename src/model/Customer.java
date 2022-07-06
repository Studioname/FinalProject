package model;

public class Customer {
	private int customerId;
	private String customerForename;
	private String customerSurname;
	private String customerAddress;
	private String customerTelephone;
	private String customerEmail;
	private String customerPaymentDetails;
	
	//blank customer
	public Customer() {
		
	}
	//user facing constructor
	public Customer(String customerForename,String customerSurname, String customerAddress,String customerTelephone,String customerEmail,String customerPaymentDetails) {
		this.customerForename = customerForename;
		this.customerSurname = customerSurname;
		this.customerAddress = customerAddress;
		this.customerTelephone = customerTelephone;
		this.customerEmail = customerEmail;
		this.customerPaymentDetails = customerPaymentDetails;
	}
	//database facing constructor
	public Customer(int customerId, String customerForename, String customerSurname, String customerAddress,String customerTelephone,String customerEmail,String customerPaymentDetails) {
		this.customerId = customerId;
		this.customerForename = customerForename;
		this.customerSurname = customerSurname;
		this.customerAddress = customerAddress;
		this.customerTelephone = customerTelephone;
		this.customerEmail = customerEmail;
		this.customerPaymentDetails = customerPaymentDetails;
	}
	
	public void printCustomerDetails() {
		System.out.println("Customer Id: " + getCustomerId());
		System.out.println("Customer Forename: " + getCustomerForename());
		System.out.println("Customer Surname: " + getCustomerSurname());
		System.out.println("Customer Address: " + getCustomerAddress());
		System.out.println("Customer Telephone: " + getCustomerTelephone());
		System.out.println("Customer Email: " + getCustomerEmail());
		System.out.println("Customer PaymentDetails: " + getCustomerPaymentDetails());
	}
	public void printBasicCustomerDetails(int index) {
		index += 1;
		System.out.println("" + index + ". " + getCustomerId() + ", " + getCustomerForename() + ", " + getCustomerSurname() + ", " + getCustomerAddress() + ", " + getCustomerTelephone());
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerForename() {
		return customerForename;
	}

	public void setCustomerForename(String customerForename) {
		this.customerForename = customerForename;
	}
	
	public String getCustomerSurname() {
		return customerSurname;
	}

	public void setCustomerSurname(String customerSurname) {
		this.customerSurname = customerSurname;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerTelephone() {
		return customerTelephone;
	}

	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPaymentDetails() {
		return customerPaymentDetails;
	}

	public void setCustomerPaymentDetails(String customerPaymentDetails) {
		this.customerPaymentDetails = customerPaymentDetails;
	}
	
}
