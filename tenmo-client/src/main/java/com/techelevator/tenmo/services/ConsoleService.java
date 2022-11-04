package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);
    private final int WIDTH = 51;
    private final Character BAR_CHARACTER = '═';

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
        printBar(BAR_CHARACTER);
        printCenterHeading("Welcome to TEnmo!", WIDTH); newLine();
        printBar(BAR_CHARACTER);
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
        System.out.printf("\nYour current account balance is: $%.2f\n", account.getBalance().doubleValue());
    }

    public void promptForTransferDetails(Transfer[] transfers, AuthenticatedUser authenticatedUser, TransferService transferService) {
        int transferId = promptForInt("\nPlease enter transfer ID to view details (0 to cancel): ");
        if (transferId != 0) {
            for (Transfer transfer : transfers) {
                if (transfer.getTransferId() == transferId) {
                    printTransferDetails(transfer);
                    if (transfer.getTransferStatus().getTransferStatusDesc().equals("Pending") && transfer.getAccountFrom().getUsername().equals(authenticatedUser.getUser().getUsername())) {
                        promptForApproveReject(transfer, authenticatedUser, transferService);
                    }
                    break;
                }
            }
        }
    }

    public void promptForApproveReject(Transfer transfer, AuthenticatedUser authenticatedUser, TransferService transferService) {
        int response = promptForApproveReject();
        if (response != 0) {
            Boolean result;
            if (response == 1) {
                result = transferService.approveTransfer(transfer, authenticatedUser);
                if (result) {
                    System.out.println("Transfer successfully approved");
                } else {
                    System.out.println("Error with approval. Transfer still pending.");
                }
            } else if (response == 2) {
                result = transferService.rejectTransfer(transfer, authenticatedUser);
                if (result) {
                    System.out.println("Transfer successfully rejected");
                } else {
                    System.out.println("Error with rejection. Transfer still pending.");
                }
            }
        }
    }

    private int promptForApproveReject() {
        int response = -1;
        while (response != 0 & response != 1 & response != 2) {
            response = promptForInt("1) Approve\n2) Reject\n0)Don't Approve or Reject");
            if (response != 0 & response != 1 & response != 2) {
                System.out.println("Please choose a valid option or 0 to exit");
            }
        }
        return response;
    }

    private void printTransferDetails(Transfer transfer) {
        newLine();
        printBar(BAR_CHARACTER);
        printCenterHeading("Transfer Details", WIDTH);
        newLine();
        printBar(BAR_CHARACTER);
        int colWidth = 8;
        printRightHeading("Id: ", colWidth);
        print(transfer.getTransferId().toString());
        printRightHeading("From: ", colWidth);
        print(transfer.getAccountFrom().getUsername());
        printRightHeading("To: ", colWidth);
        print(transfer.getAccountTo().getUsername());
        printRightHeading("Type: ", colWidth);
        print(transfer.getTransferType().getTransferTypeDesc());
        printRightHeading("Status: ", colWidth);
        print(transfer.getTransferStatus().getTransferStatusDesc());
        printRightHeading("Amount: ", colWidth);
        print(String.format("$%.2f", transfer.getAmount().doubleValue()));
    }

    private void print(String toPrint) {
        System.out.println(toPrint);
    }

    public void printTransferArray(Transfer[] transfers, AuthenticatedUser user) {
        printBar(BAR_CHARACTER);
        printCenterHeading("Transfers", WIDTH); newLine();
        printBar(BAR_CHARACTER);
        int colWidth = WIDTH / 3;
        printLeftHeading("ID", colWidth);
        printLeftHeading("From/To", colWidth);
        printRightHeading("Amount", colWidth);
        newLine();
        printBar(BAR_CHARACTER);
        //TODO: make better
        for (Transfer transfer : transfers) {
            String fromTo;
            String amount = String.format("$ %.2f", transfer.getAmount().doubleValue());
            if (transfer.getAccountFrom().getUsername().equals(user.getUser().getUsername())) {
                fromTo = "To: " + transfer.getAccountTo().getUsername();
            } else {
                fromTo = "From: " + transfer.getAccountFrom().getUsername();
            }

            printLeftHeading(transfer.getTransferId().toString(), colWidth);
            printLeftHeading(fromTo, colWidth);
            printRightHeading(amount, colWidth);
            newLine();
        }
    }

    private void newLine() {
        System.out.println();
    }
    private void printBar(char character) {
        for (int i = 0; i < WIDTH; i++) {
            System.out.print(character);
        }
        System.out.println();
    }

    private void printCenterHeading(String heading, int width) {
        int side = (width - heading.length()) / 2;
        for (int i = 0; i < side; i++) {
            System.out.print(" ");
        }
        System.out.print(heading);
        for (int i = 0; i < side; i++) {
            System.out.print(" ");
        }
    }

    private void printLeftHeading(String heading, int width) {
        int right = width - heading.length();
        System.out.print(heading);
        for (int i = 0; i < right; i++) {
            System.out.print(" ");
        }
    }

    private void printRightHeading(String heading, int width) {
        int left = width - heading.length();
        for (int i = 0; i < left; i++) {
            System.out.print(" ");
        }
        System.out.print(heading);
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }
    public Transfer promptForSend(AuthenticatedUser authenticatedUser, UserService userService) {
        TransferStatus status = new TransferStatus();
        status.setTransferStatusDesc("Approved");

        TransferType type = new TransferType();
        type.setTransferTypeDesc("Send");

        String toUsername = promptForUsername(authenticatedUser, userService);
        Account accountTo = new Account(toUsername);

        BigDecimal amount = promptForAmount();

        return new Transfer(type, status, new Account(authenticatedUser.getUser().getUsername()), accountTo, amount);
    }

    public Transfer promptForRequest(AuthenticatedUser authenticatedUser, UserService userService) {
        TransferStatus status = new TransferStatus();
        status.setTransferStatusDesc("Pending");

        TransferType type = new TransferType();
        type.setTransferTypeDesc("Request");

        String toUsername = promptForUsername(authenticatedUser, userService);
        Account accountFrom = new Account(toUsername);

        BigDecimal amount = promptForAmount();

        return new Transfer(type, status, accountFrom, new Account(authenticatedUser.getUser().getUsername()), amount);
    }

    private BigDecimal promptForAmount(){
        BigDecimal amount = new BigDecimal("0");
        while(amount.compareTo(new BigDecimal("0")) <= 0){
            //TODO: Add a break point
            amount = promptForBigDecimal("Amount: ");
            if (amount.compareTo(new BigDecimal("0")) <= 0) {
                System.out.println("Amount must be greater than zero.");
            }
        }
        return amount;
    }

    private String promptForUsername(AuthenticatedUser user, UserService userService) {
        String username = null;
        while (username == null) {
            String testUsername = promptForString("Username: ");
            //TODO: add in some way to quit out of this "exit"
            if (userService.userExistsByUsername(user, testUsername)) {
                username = testUsername;
            } else {
                System.out.println("Username does not exist, please try again.");
            }
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
