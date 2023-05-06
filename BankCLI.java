/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BankAccount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Asus
 */

class BankAccount {
    private final String name;
    private final int accountNumber;
    double balance;
    private final Date registrationDate;

    public BankAccount(String name, int accountNumber, double balance, Date registrationDate) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void deposit(double amount) {
        balance += amount;
    }
}

class Bank {
    private final ArrayList<BankAccount> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void registerAccount(String name) {
        int accountNumber = generateAccountNumber();
        double balance = 0.0;
        Date registrationDate = new Date();
        BankAccount account = new BankAccount(name, accountNumber, balance, registrationDate);
        accounts.add(account);
        System.out.println("Akun berhasil didaftarkan dengan nomor rekening: " + accountNumber);
    }

    public void sendMoney(int senderAccountNumber, int receiverAccountNumber, double amount) {
        BankAccount senderAccount = findAccount(senderAccountNumber);
        BankAccount receiverAccount = findAccount(receiverAccountNumber);

        if (senderAccount == null) {
            System.out.println("Nomor rekening pengirim tidak valid.");
        } else if (receiverAccount == null) {
            System.out.println("Nomor rekening penerima tidak valid.");
        } else if (senderAccount.getBalance() < amount) {
            System.out.println("Saldo pengirim tidak mencukupi.");
        } else {
            senderAccount.balance -= amount;
            receiverAccount.balance += amount;
            System.out.println("Transfer berhasil dilakukan.");
        }
    }

    public void addMoney(int accountNumber, double amount) {
        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Nomor rekening tidak valid.");
        } else {
            account.deposit(amount);
            System.out.println("Penambahan uang berhasil dilakukan.");
        }
    }

    public void displayAccounts() {
        System.out.println("\nDaftar Akun yang Terdaftar:");
        for (BankAccount account : accounts) {
            System.out.println("Nama: " + account.getName());
            System.out.println("Nomor Rekening: " + account.getAccountNumber());
            System.out.println("Saldo: Rp." + account.getBalance());
            System.out.println("Tanggal Pendaftaran: " + formatDate(account.getRegistrationDate()));
            System.out.println();
        }
    }

    private int generateAccountNumber() {
        return (int) (Math.random() * 900000) + 100000;
    }

    private BankAccount findAccount(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
}

public class BankCLI {
    private static final Bank bank = new Bank();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        try (scanner) {
            boolean exit = false;
            
            while (!exit) {
                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Mengosongkan karakter newline dari buffer
                
                switch (choice) {
                    case 1 -> registerAccount();
                    case 2 -> sendMoney();
                    case 3 -> addMoney();
                    case 4 -> displayAccounts();
                    case 5 -> exit = true;
                    default -> System.out.println("Pilihan tidak valid. Silakan pilih kembali.");
                }
                
                System.out.println();
            }   }
}

private static void printMenu() {
    System.out.println("=== Menu ===");
    System.out.println("1. Daftarkan akun");
    System.out.println("2. Kirim uang");
    System.out.println("3. simpan uang");
    System.out.println("4. Tampilkan akun");
    System.out.println("5. Keluar");
    System.out.print("Pilih menu: ");
}

private static void registerAccount() {
    System.out.print("\nMasukkan nama nasabah: ");
    String name = scanner.nextLine();
    bank.registerAccount(name);
}

private static void sendMoney() {
    System.out.print("\nMasukkan nomor rekening pengirim: ");
    int senderAccountNumber = scanner.nextInt();
    System.out.print("Masukkan nomor rekening penerima: ");
    int receiverAccountNumber = scanner.nextInt();
    System.out.print("Masukkan jumlah uang yang akan dikirim: ");
    double amount = scanner.nextDouble();
    scanner.nextLine(); // Mengosongkan karakter newline dari buffer

    bank.sendMoney(senderAccountNumber, receiverAccountNumber, amount);
}

private static void addMoney() {
    System.out.print("\nMasukkan nomor rekening: ");
    int accountNumber = scanner.nextInt();
    System.out.print("Masukkan jumlah uang yang akan ditambahkan: ");
    double amount = scanner.nextDouble();
    scanner.nextLine(); // Mengosongkan karakter newline dari buffer

    bank.addMoney(accountNumber, amount);
}

private static void displayAccounts() {
    bank.displayAccounts();
}
}
