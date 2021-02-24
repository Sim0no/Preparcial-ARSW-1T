package edu.eci.arsw.exams.moneylaunderingapi.persistence;

public class MoneyLaunderingException extends Exception{

    public MoneyLaunderingException(String message) {
        super(message);
    }

    public MoneyLaunderingException(String message, Throwable cause) {
        super(message, cause);
    }
}
