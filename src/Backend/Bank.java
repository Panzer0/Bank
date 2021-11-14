package Backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;
import java.util.TreeMap;

public class Bank {
    TreeMap<Integer, Account> existingAccounts;

    public Bank() {
        this.existingAccounts = new TreeMap<>();
    }

    public void openAccount(String PESEL, String name, String surname, Address address) throws InvalidPESELException {
        Account newAccount = new Account(PESEL, name, surname, address);
        this.existingAccounts.put(newAccount.getID(), newAccount);
    }
    public void closeAccount(int ID) {
        this.existingAccounts.remove(ID);
    }

    public void transferMoney(Account sender, Account recipient, BankMoney amount) throws InvalidTransactionException {
        sender.transferMoney(recipient, amount);
    }

    public Account getAccount(int ID) {
        if(!this.IDExists(ID)) throw new IllegalArgumentException("Account doesn't exist");
        return this.existingAccounts.get(ID);
    }

    public void displayAccount(Account account) {
        account.displayAccount();
    }

    public ObservableList<String> toIDList() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet())
            items.add(e.getKey() + ". " + e.getValue().getName() + " " + e.getValue().getSurname());
        return items;
    }
    public ObservableList<String> toIDListWhereName(String name) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet())
            if(e.getValue().nameEquals(name)) items.add(e.getKey() + ". " + e.getValue().getName() + " "
                    + e.getValue().getSurname());
        return items;
    }
    public ObservableList<String> toIDListWhereSurname(String surname) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet())
            if(e.getValue().surnameEquals(surname)) items.add(e.getKey() + ". " + e.getValue().getName() + " "
                    + e.getValue().getSurname());
        return items;
    }
    public ObservableList<String> toIDListWhereAddress(Address address) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet())
            if(e.getValue().addressEquals(address)) items.add(e.getKey() + ". " + e.getValue().getName() + " "
                    + e.getValue().getSurname());;
        return items;
    }
    public ObservableList<String> toIDListWhereCity(String city) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet())
            if(e.getValue().cityEquals(city)) items.add(e.getKey() + ". " + e.getValue().getName() + " "
                    + e.getValue().getSurname());
        return items;
    }
    public ObservableList<String> toIDListWhereStreet(String street) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet())
            if(e.getValue().streetEquals(street)) items.add(e.getKey() + ". " + e.getValue().getName() + " "
                    + e.getValue().getSurname());
        return items;
    }
    public ObservableList<String> toIDListWherePESEL(String PESEL) {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet())
            if(e.getValue().PESELEquals(PESEL)) items.add(e.getKey() + ". " + e.getValue().getName() + " "
                    + e.getValue().getSurname());
        return items;
    }
    public boolean IDExists(int ID) {
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet())
            if(e.getKey().equals(ID)) return true;
        return false;
    }
    
    public void displayAccounts() {
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet()) {
            displayAccount(getAccount(e.getKey()));
            System.out.println();
        }
    }

    public void displayAccountsWherePESEL(String PESEL) {
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet()) {
            if(e.getValue().PESELEquals(PESEL))
            {
                displayAccount(getAccount(e.getKey()));
                System.out.println();
            }
        }
    }

    public void displayAccountsWhereName(String name) {
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet()) {
            if(e.getValue().nameEquals(name))
            {
                displayAccount(getAccount(e.getKey()));
                System.out.println();
            }
        }
    }

    public void displayAccountsWhereSurname(String surname) {
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet()) {
            if(e.getValue().surnameEquals(surname))
            {
                displayAccount(getAccount(e.getKey()));
                System.out.println();
            }
        }
    }

    public void displayAccountsWhereAddress(Address address) {
        for (Map.Entry<Integer, Account> e : this.existingAccounts.entrySet()) {
            if(e.getValue().addressEquals(address))
            {
                displayAccount(getAccount(e.getKey()));
                System.out.println();
            }
        }
    }
}
