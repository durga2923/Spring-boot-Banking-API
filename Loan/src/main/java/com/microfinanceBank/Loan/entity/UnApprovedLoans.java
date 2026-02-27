package com.microfinanceBank.Loan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class UnApprovedLoans  implements Serializable {
    private static final long serialVersionUID= 1L;

    @Id
    private String loanId;
    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<String> reasons;

    @CreationTimestamp
    private Date date;
}
