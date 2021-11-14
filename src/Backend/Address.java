package Backend;

public class Address {
    private final int  houseNumber;
    private final String street;
    private final String city;

    public Address(String street, int houseNumber, String city) {
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public void display() {
        System.out.println(this.street + " " + this.houseNumber + ", " + this.city);
    }

    @Override
    public String toString() {
        return this.street + " " + this.houseNumber + ", " + this.city;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Address)) return false;
        Address otherAddress = (Address)other;
        return ( this.houseNumber == otherAddress.getHouseNumber() && this.street.equals(otherAddress.getStreet())
                && this.city.equals(otherAddress.getCity()) );
    }
}
