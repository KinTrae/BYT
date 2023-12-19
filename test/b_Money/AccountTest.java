package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		Nordea = new Bank("Nordea", SEK);
		Nordea.openAccount("test");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() throws AccountDoesNotExistException {
		/*
		 * Add a timed payment -> addTimedPayment(..)
		 * Remove a timed payment -> removeTimedPayment(..)
		 */
		testAccount.addTimedPayment("1", 1,1, testAccount.getBalance(), Nordea, "test");
//		assertThrows(AccountDoesNotExistException.class, ()->testAccount.addTimedPayment("1", 1,1, testAccount.getBalance(), Nordea, "test"));
		testAccount.removeTimedPayment("1");
		assertThrows(NullPointerException.class, ()->testAccount.removeTimedPayment("1"));

	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		/*
		Check if a timed payment exists -> timedPaymentExists
		 */
		assertFalse(testAccount.timedPaymentExists("1"));
		testAccount.addTimedPayment("1", 1,1, testAccount.getBalance(), Nordea, "test");
		assertTrue(testAccount.timedPaymentExists("1"));
	}

	@Test
	public void testAddWithdraw() {
		//Withdraw money from the account
		testAccount.withdraw(testAccount.getBalance());
		assertEquals(0, testAccount.getBalance().getAmount(), 0); //wyciągnięto wszystkie środki z konta => powinno być 0
	}
	
	@Test
	public void testGetBalance() {
		//Get balance of account
		assertEquals(100000.0, testAccount.getBalance().getAmount(), 0);
	}
}
