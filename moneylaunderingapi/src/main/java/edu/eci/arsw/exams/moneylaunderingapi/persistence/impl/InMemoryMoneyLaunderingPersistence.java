package edu.eci.arsw.exams.moneylaunderingapi.persistence.impl;

import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import edu.eci.arsw.exams.moneylaunderingapi.persistence.MoneyLaunderingException;
import edu.eci.arsw.exams.moneylaunderingapi.persistence.MoneyLaunderingPersistence;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryMoneyLaunderingPersistence implements MoneyLaunderingPersistence {

    List<SuspectAccount> memoria;
    public InMemoryMoneyLaunderingPersistence(){
        memoria = new ArrayList<SuspectAccount>();
        SuspectAccount cuanta = new SuspectAccount("jorge",120);
        memoria.add(cuanta);
        cuanta = new SuspectAccount("kevin",1890);
        memoria.add(cuanta);
        cuanta = new SuspectAccount("cardozo",190);
        memoria.add(cuanta);
        cuanta = new SuspectAccount("ulfredo",300);
        memoria.add(cuanta);
        cuanta = new SuspectAccount("carlitos",700);
        memoria.add(cuanta);




    }
    @Override
    public void updateAccountStatus(SuspectAccount suspectAccount) throws MoneyLaunderingException {
        SuspectAccount cuenta = null;
        for (SuspectAccount s: memoria) {
            if(suspectAccount.accountId.equals(s.accountId)){
                cuenta = s;
                break;
            }
        }
        if (cuenta == null){
            throw new MoneyLaunderingException("Cuenta Bancaria no Encontrada");
        }

        memoria.remove(cuenta);
        memoria.add(suspectAccount);


    }

    @Override
    public SuspectAccount getAccountStatus(String accountId) throws MoneyLaunderingException {
        SuspectAccount cuenta = null;
        for (SuspectAccount s: memoria) {
            if(s.accountId.equals(accountId)){
                cuenta = s;
                break;
            }
        }
        if (cuenta == null){
            throw new MoneyLaunderingException("Cuenta Bancaria no Encontrada");
        }
        return cuenta;
    }

    @Override
    public List<SuspectAccount> getSuspectAccounts() {
        return memoria;
    }

    @Override
    public void addAccount(SuspectAccount suspectAccount) throws MoneyLaunderingException {
        SuspectAccount cuenta = null;
        for (SuspectAccount s: memoria) {
            if(s.accountId.equals(suspectAccount.accountId)){
                cuenta = s;
                break;
            }
        }

        if (cuenta != null){
            throw new MoneyLaunderingException("Cuenta Bancaria ya existe");
        }

        memoria.add(suspectAccount);

    }
}
