package edu.microservices.msusers.domain;

public enum TypeEmployee {

    USER(1, "Usuário Comum"),
    ADM(2, "Administrador"),
    MODERATOR(3, "Moderador");
    
    private int cod;
    private String description;

    private TypeEmployee() {

    }

    private TypeEmployee(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static TypeEmployee toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (TypeEmployee x : TypeEmployee.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid ID: " + cod);
    }

}