package com.aws.lambda.model;

import java.math.BigInteger;
import java.util.Date;

public class Contract {
    public BigInteger contractId;
    public String contractType;
    public BigInteger accountNumber;
    public String sourceUniqueId;
    public String createdDate;

    public void setContractId(BigInteger contractId) {
        this.contractId = contractId;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public void setAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setSourceUniqueId(String sourceUniqueId) {
        this.sourceUniqueId = sourceUniqueId;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Contract() {
    }

    public Contract(BigInteger contractId, String contractType, BigInteger accountNumber, String sourceUniqueId, String createdDate) {
        this.contractId = contractId;
        this.contractType = contractType;
        this.accountNumber = accountNumber;
        this.sourceUniqueId = sourceUniqueId;
        this.createdDate = createdDate;
    }
}
