package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printAccountBalance(Account account) {
        //TODO: make better
        System.out.println(account.getBalance());
    }
    public void printTransferArray(Transfer[] transfers) {
        //TODO: make better
        for (Transfer transfer : transfers) {
            System.out.print(transfer.getTransferId() + " | ");
            System.out.print(transfer.getTransferType().getTransferTypeDesc() + " | ");
            System.out.print(transfer.getTransferStatus().getTransferStatusDesc() + " | ");
            System.out.print(transfer.getUserFrom().getUsername() + " | ");
            System.out.print(transfer.getUserTo().getUsername() + " | ");
            System.out.println(transfer.getAmount());
        }
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }
    public Transfer promptForSend(AuthenticatedUser authenticatedUser) {
        TransferStatus status = new TransferStatus();
        status.setTransferStatusDesc("Approved");

        TransferType type = new TransferType();
        type.setTransferTypeDesc("Send");

        String toUsername = promptForString("Username of recipient: ");
        User userTo = new User();
        userTo.setUsername(toUsername.trim());

        BigDecimal amount = promptForBigDecimal("Amount: ");

        return new Transfer(type, status, authenticatedUser.getUser(), userTo, amount);
    }

    public Transfer promptForRequest(AuthenticatedUser authenticatedUser) {
        TransferStatus status = new TransferStatus();
        status.setTransferStatusDesc("Pending");

        TransferType type = new TransferType();
        type.setTransferTypeDesc("Request");

        String toUsername = promptForString("Username of sender: ");
        User userFrom = new User();
        userFrom.setUsername(toUsername.trim());

        BigDecimal amount = promptForBigDecimal("Amount: ");

        return new Transfer(type, status, userFrom, authenticatedUser.getUser(), amount);
    }


    private String promptForUsername() {
        String username = null;
        while (username == null) {
            String test = promptForString("Username of sender: ");

        }

        return username;
    }


    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

}
