package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0);
		assertEquals(0.20, DKK.getRate(), 0);
		assertEquals(1.5, EUR.getRate(), 0);
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(1.0); // sets Rate to check in Test. They should be equal
		assertEquals(1, SEK.getRate(), 0);
	}
	
	@Test
	public void testGlobalValue() {
		/*
		Method universalValue should return currency.rate*currency.amount
		 */
		assertEquals(15, SEK.universalValue(100), 0);
		assertEquals(20, DKK.universalValue(100), 0);
		assertEquals(150, EUR.universalValue(100), 0);
	}
	
	@Test
	public void testValueInThisCurrency() {
		/*
		Should return the amount of money in currency A based on amount money in currency B
		f.e. I have 100SEK and i want to know how much i'd get in EUR.
		Delta = 0 cause while working in currencies we don't accept any differences
		 */

		assertEquals(10, EUR.valueInThisCurrency(100, SEK), 0);
		assertEquals(1000, SEK.valueInThisCurrency(100, EUR),0);
	}

}
