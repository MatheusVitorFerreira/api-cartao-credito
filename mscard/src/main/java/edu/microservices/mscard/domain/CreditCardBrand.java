package edu.microservices.mscard.domain;

public enum CreditCardBrand {
    MASTERCARD("Mastercard"),
    VISA("Visa"),
    ELO("Elo"),
    HIPERCARD("Hipercard"),
    AMERICAN_EXPRESS("American Express");

    private String description;

    private CreditCardBrand(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
