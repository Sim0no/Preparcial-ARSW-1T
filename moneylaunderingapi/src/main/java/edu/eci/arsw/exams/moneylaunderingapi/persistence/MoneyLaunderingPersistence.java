package edu.eci.arsw.exams.moneylaunderingapi.persistence;

import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import org.springframework.stereotype.Component;

import java.util.List;


public interface MoneyLaunderingPersistence {
    void updateAccountStatus(SuspectAccount suspectAccount) throws MoneyLaunderingException;
    SuspectAccount getAccountStatus(String accountId) throws MoneyLaunderingException;
    List<SuspectAccount> getSuspectAccounts();

    void addAccount(SuspectAccount suspectAccount) throws MoneyLaunderingException;
}
