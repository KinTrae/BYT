package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		/* Gets amount in double value. Last 2 digits in amount describes double part f.e. Money(10050, SEK) = 100,50 SEK */
		Money testSEK105 = new Money(1050, SEK);

		assertEquals(100, SEK100.getAmount(), 0);
		assertEquals(200, SEK200.getAmount(), 0);
		assertEquals(0,   SEK0.getAmount(), 0);
		assertEquals(-100, SEKn100.getAmount(), 0);
		assertEquals(10,  EUR10.getAmount(), 0);
		assertEquals(20,  EUR20.getAmount(), 0);
		assertEquals(0,   EUR0.getAmount(), 0);
		assertEquals(10.5, testSEK105.getAmount(), 0);

		assertNotEquals(10,   EUR0.getAmount(), 0);
	}

	@Test
	public void testGetCurrency() {
		/* Gets currency of Money object. The  last test checks if currency is correctly not equal the wrong one */


		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());

		assertNotEquals(SEK, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		/* Expects object representing the currency of this Money as a String.
		   f.e.  SEK100 = new Money(10000, SEK) should return "100.0 SEK"
	    */
		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("20.0 EUR", EUR20.toString());
		assertEquals("0.0 EUR", EUR0.toString());

		assertNotEquals("0 EUR", EUR0.toString());
		assertNotEquals("0EUR", EUR0.toString());
		assertNotEquals("0,00 EUR", EUR0.toString());
		assertNotEquals("0.00 EUR", EUR0.toString());
	}

	@Test
	public void testGlobalValue() {
		/* Gets the universal value of the Money, according the rate of its Currency. */
		assertEquals(0, EUR0.universalValue(), 0); //0 converted to anything should return 0
		assertEquals(15, SEK100.universalValue(), 0); //SEK.value*SET100.amount
	}

	@Test
	public void testEqualsMoney() {
		/* Check to see if the value (universal) of this money is equal to the value of another Money of some other Currency. */

		//after conversion to universal both should be true
		assertEquals((SEK100.universalValue()), EUR10.universalValue());
		assertTrue(SEK100.equals(EUR10));

		//0 != value always
		assertFalse(EUR0.equals(SEK200));

		//simple test comparing obv truths
		Money EURtest = new Money(0, EUR);
		assertTrue(EUR0.equals(EUR0)); //always true
		assertTrue(EUR0.equals(EURtest)); //comparing another Money with the same amount in the same Currency
		assertTrue(EUR0.equals(SEK0)); //0 = 0 always
	}

	@Test
	public void testAdd() {
		/* Adds a Money to this Money, regardless of the Currency of the other Money. */

		// 0SEK + 0SEK = 0SEK
		assertEquals(0, SEK0.add(SEK0).getAmount(), 0);
		assertEquals(SEK, SEK0.add(SEK0).getCurrency());

		//0SEK + 0EUR = 0SEK
		assertEquals(0, SEK0.add(EUR0).getAmount(), 0);
		assertEquals(SEK, SEK0.add(EUR0).getCurrency());

		// 0SEK + 200SEK = 200SEK
		assertEquals(200, SEK0.add(SEK200).getAmount(), 0);
		assertEquals(SEK, SEK0.add(SEK200).getCurrency());

		// 10EUR = 100SEK => 100SEK + 10EUR = 200SEK
		assertEquals(200, SEK100.add(EUR10).getAmount(), 0);
		assertEquals(SEK, SEK100.add(EUR10).getCurrency());

	}

	@Test
	public void testSub() {
		/* Subtracts a Money from this Money, regardless of the Currency of the other Money. */

		Money testSEK0 = SEK0.sub(SEK0);
		assertEquals(0, testSEK0.getAmount(),0); // 0-0=0
		assertEquals(SEK, testSEK0.getCurrency()); //0-0=0

		Money testSEK200 = SEK0.sub(EUR0);
		assertEquals(200, SEK200.sub(SEK0).getAmount(), 0); // 200-0 = 200
		assertEquals(SEK, testSEK200.getCurrency());

		Money testSek0v2 = SEK100.sub(EUR10);
		assertEquals(0, testSek0v2.getAmount(), 0); // 10EUR = 100SEK => 100SEK - 10EUR = 0SEK
		assertEquals(SEK, testSek0v2.getCurrency());
	}

	@Test
	public void testIsZero() {
		/* Check to see if the amount of this Money is zero or not */

		//0
		assertTrue(SEK0.isZero());
		assertTrue(EUR0.isZero());

		//!0
		assertFalse(EUR10.isZero());
	}

	@Test
	public void testNegate() {
		/* Negate the amount of money, i.e. if the amount is 10.0 SEK the negation returns -10.0 SEK */

		assertEquals(0, SEK0.negate().getAmount(),0); //0 negate 0 should be 0
		assertEquals(100, SEKn100.negate().getAmount(),0);
		assertEquals(-100, SEK100.negate().getAmount(), 0);

		assertNotEquals(100, SEK100.negate().getAmount(), 0); // 100 negate should be -100
	}

	@Test
	public void testCompareTo() {
		/*
		return 0 if the values of the money are equal.
	           A negative integer if this Money is less valuable than the other Money.
	           A positive integer if this Money is more valuable than the other Money.
		 */
		assertEquals(0, SEK0.compareTo(EUR0));

		assertTrue(SEK0.compareTo(EUR10) < 0);
		assertTrue(EUR10.compareTo(SEK0) > 0);
		assertTrue(EUR20.compareTo(EUR10) > 0);
	}
}
