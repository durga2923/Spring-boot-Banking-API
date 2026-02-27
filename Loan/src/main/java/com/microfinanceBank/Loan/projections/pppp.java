package com.microfinanceBank.Loan.projections;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

public interface pppp {


    Long id();
    BigDecimal paymentAmount();
    LocalDate PaymentDate();

    Time time();
}
