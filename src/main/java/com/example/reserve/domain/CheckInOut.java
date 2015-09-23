package com.example.reserve.domain;


public class CheckInOut {
	private String checkin;
	private String checkout;
	
	public CheckInOut(){};
	
	public CheckInOut(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
	}

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
}
