package com.techelevator.tenmo.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    @NotNull(message ="Transfer Type must not be null.")
    private TransferType transferType;
    @NotNull(message ="Transfer Status must not be null.")
    private TransferStatus transferStatus;
    @NotNull(message ="User From must not be null.")
    private User userFrom;
    private Account accountFrom;
    @NotNull(message ="User to must not be null.")
    private User userTo;
    private Account accountTo;
    @NotNull(message ="Amount must not be null.")
    @Positive(message = "Amount must be greater than zero.")
    private BigDecimal amount;

    public Transfer() {
    }

    public Transfer(TransferType transferType, TransferStatus transferStatus, User userFrom, User userTo, BigDecimal amount) {
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
