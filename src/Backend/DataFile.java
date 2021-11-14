package Backend;

import java.io.*;
import java.util.Map;


public class DataFile {
    String fileName;
    Bank bank;

    public DataFile(String name) throws FileNotFoundException {
        if(!name.endsWith(".txt")) throw(new FileNotFoundException("Invalid file extension"));
        fileName = name;
        bank = new Bank();
    }


    // Extracts all data from myFile
    public Bank extractBank() throws FileNotFoundException, IOException, IllegalArgumentException{
        try{
            File myFile = new File(this.fileName);
            if(!myFile.isFile()){
                throw new FileNotFoundException("File not found.");
            }
            String tempCategoryName;
            BufferedReader fileReader = new BufferedReader(new FileReader(this.fileName));

            String PESEL, name, surname, city, street, houseNumber;
            BankMoney balance;
            Address address;

            String line;
            for(int i = 0;; i++){
                line = fileReader.readLine();
                while(line != null && !line.startsWith("#")){
                    line = fileReader.readLine();
                }
                if(line == null || line.isBlank()) return bank;

                PESEL = line.substring(1);
                name = fileReader.readLine();
                surname = fileReader.readLine();
                city = fileReader.readLine();
                street = fileReader.readLine();
                houseNumber = fileReader.readLine();
                balance = new BankMoney(fileReader.readLine());
                address = new Address(street, Integer.parseInt(houseNumber), city);

                bank.openAccount(PESEL, name, surname, address);
                bank.getAccount(i).depositMoney(balance);
            }
        }
        catch(FileNotFoundException e){
            throw new FileNotFoundException("File not found");
        }
         catch (IOException | InvalidPESELException | InvalidTransactionException e) {
            throw new IllegalArgumentException("File corrupted");
        }
    }


    public void saveData() throws IOException {
        FileWriter writer = new FileWriter(this.fileName);
        for (Map.Entry<Integer, Account> e : this.bank.existingAccounts.entrySet()) {
            writer.write("#");
            Account account = bank.getAccount(e.getKey());
            Address address = account.getAddress();
            writer.write(account.getPESEL() + '\n');
            writer.write(account.getName() + '\n');
            writer.write(account.getSurname() + '\n');
            writer.write(address.getCity() + '\n');
            writer.write(address.getStreet() + '\n');
            writer.write(String.valueOf(address.getHouseNumber()) + '\n');
            writer.write(account.getBalanceNoCurr() + '\n');
        }
        writer.close();
    }
}