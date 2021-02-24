package edu.eci.arsw.exams.moneylaunderingapi.service;

import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import edu.eci.arsw.exams.moneylaunderingapi.persistence.MoneyLaunderingException;
import edu.eci.arsw.exams.moneylaunderingapi.persistence.MoneyLaunderingPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyLaunderingServiceStub implements MoneyLaunderingService {

    @Autowired
    MoneyLaunderingPersistence moneyLaunderingPrstc;

    @Override
    public void updateAccountStatus(SuspectAccount suspectAccount) throws MoneyLaunderingException {
        moneyLaunderingPrstc.updateAccountStatus(suspectAccount);
    }

    @Override
    public SuspectAccount getAccountStatus(String accountId) throws MoneyLaunderingException {
        return moneyLaunderingPrstc.getAccountStatus(accountId);
    }

    @Override
    public List<SuspectAccount> getSuspectAccounts() {
        return moneyLaunderingPrstc.getSuspectAccounts();
    }

    @Override
    public void addAccount(SuspectAccount suspectAccount) throws MoneyLaunderingException {
        moneyLaunderingPrstc.addAccount(suspectAccount);
    }
}
