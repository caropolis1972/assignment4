/**
 * DO NOT MODIFY THIS FILE
 *
 * INSTEAD:
 *
 * 1. Scroll down on this page within HackerRank
 *
 * 2. Click "Download code as Zip"
 *
 * 3. Find the zip file (e.g. in your Downloads folder) and unzip it
 *
 * 4. Move the "assignment3" folder to your coding projects folder (e.g. Eclipse workspace)
 *
 * 5. Open Eclipse
 *
 * 6. Click File -> Import
 *
 * 7. Select "Maven -> Existing Maven Projects" and press Next
 *
 * 8. Click Browse and find the assignment1 folder, select the assignment1 folder
 *
 * 9. Ensure the pom.xml Project is selected and click Finish
 *
 * 10. Implement your solution in src/main/java
 *
 * 11. Right-click on the "assignment1" project in Eclipse, select "Run As" and "Java Application" and
 *     select the MeritAmericaBankApp to run your main() method
 *
 * 12. Right-click on the "assignment1" project in Eclipse, select "Run As" and "JUnit Test" to run our tests to verify your solution
 *
 * 13. Once complete, zip your "assignment1" folder, and upload it to HackerRank by clicking on "Upload code as Zip"
 *
 * 14. You can click on the "Run Unit Tests" button to confirm it is working.
 */
package com.meritamerica.assignment4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.meritamerica.assignment4.AccountHolder;
import com.meritamerica.assignment4.CDAccount;
import com.meritamerica.assignment4.CDOffering;
import com.meritamerica.assignment4.CheckingAccount;
import com.meritamerica.assignment4.MeritBank;
import com.meritamerica.assignment4.SavingsAccount;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolutionTest {
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testCreateAccountHolder(){
        AccountHolder accountHolder = new AccountHolder(
        	"Sadiq",
        	"",
        	"Manji",
        	"123456789");
        
        assertEquals("Sadiq", accountHolder.getFirstName());
        assertEquals("", accountHolder.getMiddleName());
        assertEquals("Manji", accountHolder.getLastName());
        assertEquals("123456789", accountHolder.getSSN());
        assertEquals(0, accountHolder.getNumberOfCDAccounts());
        assertEquals(0, accountHolder.getNumberOfCheckingAccounts());
        assertEquals(0, accountHolder.getNumberOfSavingsAccounts());
    }

    @Test
    public void testDepositPositiveAmountInCheckingAccount() throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitexception{
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	CheckingAccount checkingAccount = accountHolder.addCheckingAccount(0);
        
        assertEquals(true, checkingAccount.deposit(500.0));
    }

    @Test
    public void testDepositNegativeAmountInCheckingAccount() throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitexception{
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	CheckingAccount checkingAccount = accountHolder.addCheckingAccount(500);
        
        assertEquals(false, checkingAccount.deposit(-500.0));
    }

    @Test
    public void testDepositPositiveAmountInSavingsAccount() throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitexception{
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	SavingsAccount savingsAccount = accountHolder.addSavingsAccount(0);
        
        assertEquals(true, savingsAccount.deposit(500.0));
    }

    @Test
    public void testDepositNegativeAmountInSavingsAccount() throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitexception{
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	SavingsAccount savingsAccount = accountHolder.addSavingsAccount(500);
        
        assertEquals(false, savingsAccount.deposit(-500.0));
    }

    @Test
    public void testWithdrawAmountExceedingBalanceInCheckingAccount() throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitexception{
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	CheckingAccount checkingAccount = accountHolder.addCheckingAccount(50);
        
        assertEquals(false, checkingAccount.withdraw(500.0));
    }

    @Test
    public void testWithdrawAmountExceedingBalanceInSavingsAccount() throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitexception{
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	SavingsAccount savingsAccount = accountHolder.addSavingsAccount(50);
        
        assertEquals(false, savingsAccount.withdraw(500.0));
    }
    
    @Test(expected = ExceedsFraudSuspicionLimitexception.class)
    public void testWithdrawFromCDAccount() throws NegativeAmountException, ExceedsFraudSuspicionLimitexception{
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	CDAccount cdAccount = accountHolder.addCDAccount(new CDOffering(5, 0.03), 10000);
        
        assertEquals(false, cdAccount.withdraw(500.0));
    }
    
    @Test(expected = ExceedsFraudSuspicionLimitexception.class)
    public void testDepositIntoCDAccount() throws NegativeAmountException, ExceedsFraudSuspicionLimitexception{
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	CDAccount cdAccount = accountHolder.addCDAccount(new CDOffering(5, 0.03), 10000);
        
        assertEquals(false, cdAccount.deposit(500.0));
    }

    @Test
    public void testFutureValueInSavingsAccount() throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitexception{
        AccountHolder accountHolder = new AccountHolder(
        	"Sadiq",
        	"",
        	"Manji",
        	"123456789");
        
        SavingsAccount savingsAccount = accountHolder.addSavingsAccount(100);

        double fv = 100.0 * Math.pow(1 + 0.01, 3);
        
        assertEquals(fv, savingsAccount.futureValue(3), 0.1);
        
        assertEquals(100.03000003, 100.03000004, 0.1);
    }
    

    @Test
    public void testFutureValueInCheckingAccount() throws ExceedsCombinedBalanceLimitException, NegativeAmountException, ExceedsFraudSuspicionLimitexception{
        AccountHolder accountHolder = new AccountHolder(
        	"Sadiq",
        	"",
        	"Manji",
        	"123456789");
        
        CheckingAccount checkingAccount = accountHolder.addCheckingAccount(100);

        double fv = 100.0 * Math.pow(1 + 0.0001, 3);
        
        assertEquals(fv, checkingAccount.futureValue(3), 0.1);
    }
    
    @Test(expected = ExceedsFraudSuspicionLimitexception.class)
    public void testFutureValueInCDAccount() throws NegativeAmountException, ExceedsFraudSuspicionLimitexception{
        AccountHolder accountHolder = new AccountHolder(
        	"Sadiq",
        	"",
        	"Manji",
        	"123456789");
        
        CDAccount cdAccount = accountHolder.addCDAccount(new CDOffering(5, 0.03), 10000);

        double fv = 10000.0 * Math.pow(1 + 0.03, 5);
        
        assertEquals(fv, cdAccount.futureValue(), 0.1);
    }
    
    @Test
    public void testSuccessfulReadCDOfferingFromString(){
        CDOffering cdOffering = CDOffering.readFromString("3,0.019");
        
        assertEquals(3, cdOffering.getTerm());
        assertEquals(0.019, cdOffering.getInterestRate(), 0.1);
    }
    
    @Test
    public void testFailedReadCDOfferingFromString1(){
    	exceptionRule.expect(NumberFormatException.class);
        CDOffering cdOffering = CDOffering.readFromString("30.019");
        
        assertEquals(null, cdOffering);
    }
    
    @Test
    public void testFailedReadCDOfferingFromString2(){
    	exceptionRule.expect(NumberFormatException.class);
        CDOffering.readFromString("a,0.019");
    }
    
    @Test
    public void testFailedReadCDOfferingFromString3(){
    	exceptionRule.expect(NumberFormatException.class);
        CDOffering.readFromString("3,b");
    }
    
    @Test
    public void testSuccessfulReadCheckingAccountFromString() throws ParseException{
    	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = dateFormatter.parse("01/01/2020");
    	
        CheckingAccount checkingAccount = CheckingAccount.readFromString("82,1000,0.00015,01/01/2020");
        
        assertEquals(82, checkingAccount.getAccountNumber());
        assertEquals(1000.0, checkingAccount.getBalance(), 0);
        assertEquals(0.00015, checkingAccount.getInterestRate(), 0);
        assertEquals(date, checkingAccount.getOpenedOn());
    }
    
    @Test
    public void testFailedReadCheckingAccountFromString1() throws ParseException{
    	exceptionRule.expect(NumberFormatException.class);
    	CheckingAccount checkingAccount = CheckingAccount.readFromString("82,1000,0.0001501/01/2020");
        
        assertEquals(null, checkingAccount);
    }
    
    @Test
    public void testFailedReadCheckingAccountFromString2() throws ParseException{
    	exceptionRule.expect(NumberFormatException.class);
    	CheckingAccount.readFromString("a,1000,0.00015,01/01/2020");
    }
    
    @Test
    public void testSuccessfulReadSavingsAccountFromString() throws ParseException{
    	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = dateFormatter.parse("01/02/2020");
    	
        SavingsAccount savingsAccount = SavingsAccount.readFromString("83,10000,0.02,01/02/2020");
        
        assertEquals(83, savingsAccount.getAccountNumber());
        assertEquals(10000.0, savingsAccount.getBalance(), 0);
        assertEquals(0.02, savingsAccount.getInterestRate(), 0);
        assertEquals(date, savingsAccount.getOpenedOn());
    }
    
    @Test
    public void testFailedReadSavingsAccountFromString1() throws ParseException{
    	exceptionRule.expect(NumberFormatException.class);
    	SavingsAccount.readFromString("82,1000,0.0001501/01/2020");
    }
    
    @Test
    public void testFailedReadSavingsAccountFromString2() throws ParseException{
    	exceptionRule.expect(NumberFormatException.class);
    	SavingsAccount.readFromString("a,1000,0.00015,01/01/2020");
    }
    
    @Test
    public void testSuccessfulReadCDAccountFromString() throws ParseException{
    	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = dateFormatter.parse("01/03/2020");
    	
        CDAccount cdAccount = CDAccount.readFromString("84,20000,0.03,01/03/2020,5");
        
        assertEquals(84, cdAccount.getAccountNumber());
        assertEquals(20000.0, cdAccount.getBalance(), 0);
        assertEquals(0.03, cdAccount.getInterestRate(), 0);
        assertEquals(date, cdAccount.getOpenedOn());
        assertEquals(5, cdAccount.getTerm());
    }
    
    @Test
    public void testFailedReadCDAccountFromString1() throws ParseException{
    	exceptionRule.expect(NumberFormatException.class);
    	CDAccount.readFromString("82,1000,0.0001501/01/2020,5");
    }
    
    @Test
    public void testFailedReadCDAccountFromString2() throws ParseException{
    	exceptionRule.expect(NumberFormatException.class);
    	CDAccount.readFromString("a,1000,0.00015,01/01/2020,5");
    }
    
    @Test
    public void testSuccessfullyReadFromFile() {
    	 	
    	boolean result = MeritBank.readFromFile("src/test/testMeritBank_good.txt");
    	
    	assertEquals(true, result);
    	assertEquals(11, MeritBank.getNextAccountNumber());
    	assertEquals(3, MeritBank.getCDOfferings().length);
    	
    	AccountHolder[] sortedAccountHolders = MeritBank.sortAccountHolders();    	
    	
    	assertEquals(2051.0, sortedAccountHolders[0].getCombinedBalance(), 0);
    	assertEquals(2560.0, sortedAccountHolders[1].getCombinedBalance(), 0);
    	
    }
    
    @Test
    public void testFailedReadFromFile() {    	
    	boolean result = MeritBank.readFromFile("src/test/testMeritBank_good.txt");
    	assertEquals(true, result);
    	
    	result = MeritBank.readFromFile("src/test/testMeritBank_bad.txt");
    	assertEquals(false, result);

    	assertEquals(11, MeritBank.getNextAccountNumber());
    	assertEquals(3, MeritBank.getCDOfferings().length);
    }
    
    @Test(expected = NegativeAmountException.class)
    public void testProcessTransactionThrowNegativeAmountException() throws Exception {   
    	
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	CheckingAccount checkingAccount = accountHolder.addCheckingAccount(50);
    	
    	DepositTransaction depositTransaction = new DepositTransaction(checkingAccount, -50);
    	MeritBank.processTransaction(depositTransaction);
    }
    
    @Test(expected = ExceedsAvailableBalanceException.class)
    public void testProcessTransactionThrowExceedsAvailableBalanceException() throws Exception {   
    	
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	CheckingAccount checkingAccount = accountHolder.addCheckingAccount(50);
    	
    	WithdrawTransaction withdrawTransaction = new WithdrawTransaction(checkingAccount, 1000);
    	MeritBank.processTransaction(withdrawTransaction);
    }   
    
    @Test(expected = ExceedsFraudSuspicionLimitexception.class)
    public void testProcessTransactionThrowExceedsFraudSuspicionLimitException() throws Exception {   
    	
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	CheckingAccount checkingAccount = accountHolder.addCheckingAccount(5000);
    	
    	WithdrawTransaction withdrawTransaction = new WithdrawTransaction(checkingAccount, 2000);
    	MeritBank.processTransaction(withdrawTransaction);
    }   
    
    @Test
    public void testProcessTransactionSuccessfully() throws Exception {   
    	
    	AccountHolder accountHolder = new AccountHolder(
            	"Sadiq",
            	"",
            	"Manji",
            	"123456789");
    	
    	CheckingAccount checkingAccount = accountHolder.addCheckingAccount(500);
    	
        SavingsAccount savingsAccount = accountHolder.addSavingsAccount(100);
    	
        TransferTransaction transferTransaction = new TransferTransaction(checkingAccount, savingsAccount, 200);
    	MeritBank.processTransaction(transferTransaction);
    	
        assertEquals(300, checkingAccount.getBalance(), 0);
        assertEquals(300, savingsAccount.getBalance(), 0);
    	
    }  
}