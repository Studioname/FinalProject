package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayTest {

	@Test
	void testGetFormattedPrice() {
		Play p = new Play("Cats", "A show about cats", "12:00:00", "2022-12-10", "02:00:00", 5000, 5000);
		assertEquals("£1000.00", p.getFormattedPrice(100000));
	}
	
	@Test
	void testPrintPlayDetails() {
		Play p = new Play("Cats", "A show about cats", "12:00:00", "2022-12-10", "02:00:00", 5000, 5000);
		p.printPlayDetails();
	}

}
