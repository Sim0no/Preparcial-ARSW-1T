package edu.eci.arsw.exams.moneylaunderingapi.service;

import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import edu.eci.arsw.exams.moneylaunderingapi.persistence.MoneyLaunderingException;

import java.util.List;

public interface MoneyLaunderingService {
    void updateAccountStatus(SuspectAccount suspectAccount) throws MoneyLaunderingException;
    SuspectAccount getAccountStatus(String accountId) throws MoneyLaunderingException;
    List<SuspectAccount> getSuspectAccounts();
    void addAccount(SuspectAccount suspectAccount) throws MoneyLaunderingException;
}
