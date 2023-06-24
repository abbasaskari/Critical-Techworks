package banking;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Bank implementation.
 */
public class Bank implements BankInterface {
    private LinkedHashMap<Long, Account> accounts;

    public Bank() {
        this.accounts = new LinkedHashMap<>();
    }

    private Account getAccount(Long accountNumber) {
        return this.accounts.get(accountNumber);
    }

    public Long openCommercialAccount(Company company, int pin, double startingDeposit) {
        long accountNumber = this.accounts.size() + 1;
        this.accounts.put(accountNumber, new CommercialAccount(company, accountNumber, pin, startingDeposit));
        return accountNumber;
    }

    public Long openConsumerAccount(Person person, int pin, double startingDeposit) {
        long accountNumber = this.accounts.size() + 1;
        this.accounts.put(accountNumber, new ConsumerAccount(person, accountNumber, pin, startingDeposit));
        return accountNumber;

    }

    public double getBalance(Long accountNumber) {
        if (this.accounts.get(accountNumber) == null)
            return -1.0;
        return this.accounts.get(accountNumber).getBalance();
    }

    public void credit(Long accountNumber, double amount) {
        this.accounts.get(accountNumber).creditAccount(amount);
    }

    public boolean debit(Long accountNumber, double amount) {
        return this.accounts.get(accountNumber).debitAccount(amount);
    }

    public boolean authenticateUser(Long accountNumber, int pin) {
        return this.accounts.get(accountNumber).validatePin(pin);
    }

    public void addAuthorizedUser(Long accountNumber, Person authorizedPerson) {
        ((CommercialAccount) this.accounts.get(accountNumber)).addAuthorizedUser(authorizedPerson);
    }

    public boolean checkAuthorizedUser(Long accountNumber, Person authorizedPerson) {
        if (this.accounts.get(accountNumber) instanceof CommercialAccount)
            return ((CommercialAccount) this.accounts.get(accountNumber)).isAuthorizedUser(authorizedPerson);
        else
            return false;
    }

    public Map<String, Double> getAverageBalanceReport() {
        Map<String, Double> avgMap = new HashMap<>();

        int commercialAccountCount = 0, consumerAccountCount = 0;
        double commercialAccountSum = 0, consumerAccountSum = 0;
        for (Account account : this.accounts.values()) {
            if (account instanceof CommercialAccount) {
                commercialAccountCount++;
                commercialAccountSum += account.getBalance();
            } else {
                consumerAccountCount++;
                consumerAccountSum += account.getBalance();
            }
        }

        avgMap.put("CommercialAccount", commercialAccountSum / commercialAccountCount);
        avgMap.put("ConsumerAccount", consumerAccountSum / consumerAccountCount);
        return avgMap;
    }
}
