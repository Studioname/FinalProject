package model;

public class Customer {
	private int customerId;
	private String customerName;
	private String customerAddress;
	private String customerTelephone;
	private String customerEmail;
	private String customerPaymentDetails;
	//user facing constructor
	public Customer(String customerName,String customerAddress,String customerTelephone,String customerEmail,String customerPaymentDetails) {
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerTelephone = customerTelephone;
		this.customerEmail = customerEmail;
		this.customerPaymentDetails = customerPaymentDetails;
	}
	//database facing constructor
	public Customer(int customerId, String customerName,String customerAddress,String customerTelephone,String customerEmail,String customerPaymentDetails) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerTelephone = customerTelephone;
		this.customerEmail = customerEmail;
		this.customerPaymentDetails = customerPaymentDetails;
	}
	
	public void printCustomerDetails() {
		System.out.println("Customer Id: " + getCustomerId());
		System.out.println("Customer Address: " + getCustomerName());
		System.out.println("Customer Telephone: " + getCustomerAddress());
		System.out.println("Customer Email: " + getCustomerTelephone());
		System.out.println("Customer PaymentDetails: " + getCustomerPaymentDetails());
	}
	public void printBasicCustomerDetails(int index) {
		index += 1;
		System.out.println("" + index + ". " + getCustomerId() + ", " + getCustomerName() + ", " + getCustomerAddress() + ", " + getCustomerTelephone());
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
