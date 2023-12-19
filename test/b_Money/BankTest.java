package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	String accountId, accountId2;

	Money money;
	
	@Before
	public void setUp() throws Exception {
		accountId = "1";
		accountId2 = "2";
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		money = new Money(100, SEK);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");

		//opened account id: "1", "2"
		SweBank.openAccount(accountId);
		SweBank.openAccount(accountId2);
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test()
	public void testOpenAccount() throws AccountExistsException {
		/*Open an account at this bank. Throws AccountExistsException If the account already exists */
		assertThrows(AccountExistsException.class, ()-> SweBank.openAccount(accountId));
	}

	@Test()
	public void testDeposit() throws AccountDoesNotExistException, AccountExistsException {
		/* Deposit money to an account. Throws AccountDoesNotExistException If the account does not exist */
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.deposit("accountId", money)); //assertion Error: unexpected exception type thrown; expected:<b_Money.AccountDoesNotExistException> but was:<java.lang.NullPointerException>

		//correct way: putting money inside an existing account
		SweBank.deposit(accountId, money);
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		/* Withdraw money from an account. Throws AccountDoesNotExistException If the account does not exist */
		assertThrows(AccountDoesNotExistException.class, ()-> SweBank.withdraw("accountId", money));

		//correct way: withdraw from existing account
		SweBank.withdraw(accountId, money);
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		/* Get the balance of an account. Throws AccountDoesNotExistException If the account does not exist */
		assertThrows(AccountDoesNotExistException.class, ()-> SweBank.getBalance("accountId"));

		//correct way: get balance of an existing account
		SweBank.getBalance(accountId);
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		/*
		 * Transfer money between two accounts
		 * @param fromaccount Id of account to deduct from in this Bank
		 * @param tobank Bank where receiving account resides
		 * @param toaccount Id of receiving account
		 * @param amount Amount of Money to transfer
		 * @throws AccountDoesNotExistException If one of the accounts do not exist
		 */

		assertThrows(AccountDoesNotExistException.class, ()->SweBank.transfer(accountId, "accountId2", money)); //java.lang.AssertionError: expected b_Money.AccountDoesNotExistException to be thrown, but nothing was thrown

		//correct way: both accounts exist
		SweBank.transfer(accountId, accountId2, money);
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		/*
		Add a timed payment -> addTimedPayment(String accountid, String payid, Integer interval, Integer next, Money amount, Bank tobank, String toaccount)
		removeTimedPayment -> removeTimedPayment(String accountid, String id)
		 */
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.addTimedPayment("accountId", "1", 1, 1, money, SweBank, accountId));
		SweBank.addTimedPayment(accountId, "1", 1, 1, money, SweBank, accountId);

		assertThrows(AccountDoesNotExistException.class, () -> SweBank.removeTimedPayment("accountId", "1"));
		SweBank.removeTimedPayment(accountId, "1");

	}
}
