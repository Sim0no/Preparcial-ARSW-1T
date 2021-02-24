package edu.eci.arsw.exams.moneylaunderingapi;


import edu.eci.arsw.exams.moneylaunderingapi.model.SuspectAccount;
import edu.eci.arsw.exams.moneylaunderingapi.persistence.MoneyLaunderingException;
import edu.eci.arsw.exams.moneylaunderingapi.service.MoneyLaunderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoneyLaunderingController {
    @Autowired
    MoneyLaunderingService moneyLaunderingService;

    @GetMapping("/")
    public ResponseEntity<?> main() {
        return new ResponseEntity<>("Preparcial German Marin", HttpStatus.OK);
    }
    @GetMapping("/fraud-bank-accounts")
    public ResponseEntity<?> getFraudAccounts() {
        return new ResponseEntity<>(moneyLaunderingService.getSuspectAccounts(), HttpStatus.OK);
    }

    @PostMapping("/fraud-bank-accounts")
    public ResponseEntity<?> addFraudAccount(@RequestBody SuspectAccount cuentaSospechoza){
        try {
            moneyLaunderingService.addAccount(cuentaSospechoza);
        } catch (MoneyLaunderingException e) {
            return new ResponseEntity<>("Cuenta bancaria ya existe.",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @GetMapping("/fraud-bank-accounts/{accountId}")
    public ResponseEntity<?> getFraudAccountById(@PathVariable String accountId) {
        try {
            return new ResponseEntity<>(moneyLaunderingService.getAccountStatus(accountId), HttpStatus.OK);
        } catch (MoneyLaunderingException e) {
            return new ResponseEntity<>("Cuenta Bancaria no existente.",HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/fraud-bank-accounts/{accountId}")
    public ResponseEntity<?> updateFraudAccount(@PathVariable String accountId, @RequestBody SuspectAccount cuentaSospechoza){

        try {
            moneyLaunderingService.updateAccountStatus(cuentaSospechoza);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MoneyLaunderingException e) {
            return new ResponseEntity<>("Cuenta Bancaria no existente.",HttpStatus.NOT_FOUND);
        }
    }




}
