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
    private Account accountFrom;
    private Account accountTo;
    @NotNull(message ="Amount must not be null.")
    @Positive(message = "Amount must be greater than zero.")
    private BigDecimal amount;

    public Transfer() {
    }

    public Transfer(TransferType transferType, TransferStatus transferStatus, Account accountFrom, Account accountTo, BigDecimal amount) {
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
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

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
