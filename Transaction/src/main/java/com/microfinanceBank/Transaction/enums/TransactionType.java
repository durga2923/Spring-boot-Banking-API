package com.microfinanceBank.Transaction.enums;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


public enum  TransactionType {
    WITHDRAW,DEPOSIT,TRANSFER
}
