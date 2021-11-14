package Backend;

public class Account {
    private static int availableID = 0;

    private final int ID;
    private final String PESEL;
    private final String name, surname;
    private final Address address;

    BankMoney balance;


    public Account(String PESEL, String name, String surname, Address address) throws InvalidPESELException {
        validatePESEL(PESEL);

        this.ID = availableID++;
        this.PESEL = PESEL;
        this.name = name;
        this.surname = surname;
        this.balance = new BankMoney();
        this.address = address;
    }

    public void validatePESEL(String PESEL) throws InvalidPESELException {
        if(PESEL.length() != 11)    throw new InvalidPESELException("Invalid PESEL length - must be 11");
        if(!PESEL.matches("[0-9]+"))    throw new InvalidPESELException("Non-numeric PESEL value");
    }

    public int getID() {
        return ID;
    }

    public String getPESEL() {
        return this.PESEL;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }
    public Address getAddress() {
        return this.address;
    }

    public String getDisplayAddress() {
        return this.address.toString();
    }

    public String getDisplayBalance() {
        return this.balance.toString();
    }
    public String getBalanceNoCurr() {
        return this.balance.toStringNoCurrency();
    }

    public void depositMoney(BankMoney amount) throws IllegalArgumentException, InvalidTransactionException {
        if(amount.isNegative()) throw new InvalidTransactionException("Can't deposit negative funds");
        this.balance.addAmount(amount);
    }
    public void withdrawMoney(BankMoney amount) throws IllegalArgumentException, InvalidTransactionException {
        if(amount.isNegative()) throw new InvalidTransactionException("Can't withdraw negative funds");
        if(this.balance.compareTo(amount) < 0) throw new InvalidTransactionException("Insufficient funds");
        this.balance.subtractAmount(amount);
    }

    public void transferMoney(Account recipient, BankMoney amount) throws InvalidTransactionException {
        this.withdrawMoney(amount);
        recipient.depositMoney(amount);
    }

    public void displayBalance() {
        this.balance.display();
    }
    public void displayPESEL() {
        System.out.println(this.PESEL);
    }

    public void displayFullName() {
        System.out.println(this.name + " " + this.surname);
    }

    public void displayAddress() {
        this.address.display();
    }

    public void displayID() {
        System.out.println(this.ID);
    }

    public void displayAccount() {
        System.out.print("Account #");
        this.displayID();

        System.out.print("PESEL: ");
        this.displayPESEL();

        System.out.print("Full name: ");
        this.displayFullName();

        System.out.print("Address: ");
        this.displayAddress();

        System.out.print("Balance: ");
        this.displayBalance();
    }


    public boolean PESELEquals(String PESEL) {
        return(this.PESEL.equals(PESEL));
    }
    public boolean nameEquals(String name) {
        System.out.println("Comparing " + name + "with" + this.name);
        return(this.name.equals(name));
    }
    public boolean surnameEquals(String surname) {
        return(this.surname.equals(surname));
    }
    public boolean addressEquals(Address address) {
        return(this.address.equals(address));
    }
    public boolean streetEquals(String street) {
        return(this.address.getStreet().equals(street));
    }
    public boolean cityEquals(String city) {
        return(this.address.getCity().equals(city));
    }

    @Override
    public String toString() {
        return  "Account #" + ID +
                "\nFull name: " + name + " " + surname +
                "\nPESEL: " + PESEL +
                "\nAddress: " + address.toString() +
                "\nBalance: " + balance.toString()
                ;
    }
}
