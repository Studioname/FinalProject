package model;

public class Customer {
	private String customerName;
	private String customerAddress;
	private String customerTelephone;
	private String customerEmail;
	private String customerPaymentDetails;
	
	public Customer(String customerName,String customerAddress,String customerTelephone,String customerEmail,String customerPaymentDetails) {
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerTelephone = customerTelephone;
		this.customerEmail = customerEmail;
		this.customerPaymentDetails = customerPaymentDetails;
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
