package com.viniciusfk.bankApi.service;

import com.viniciusfk.bankApi.dto.TransactionDto;
import com.viniciusfk.bankApi.records.ResponseResult;
import com.viniciusfk.bankApi.model.User;
import com.viniciusfk.bankApi.repository.user.IUser;
import org.springframework.stereotype.Service;

@Service
public class BankingService {
    private final IUser repository;

    public BankingService(IUser repository) {
        this.repository = repository;
    }

    public double getOwnBalance(String requester){
        User origin = repository.findByNameOrCpf(requester, "");
        return origin.getBalance();
    }

    public ResponseResult deposit(String requester, double ammount){
        if(ammount > 0){
            User origin = repository.findByName(requester);
            origin.setBalance(origin.getBalance() + ammount);
            repository.save(origin);
            return new ResponseResult(true, "Deposit sucessful");
        }
        return new ResponseResult(false, "Invalid ammount");
    }

    public ResponseResult withdraw(String requester, double ammount){
        if(ammount > 0){
            User origin = repository.findByName(requester);

            if (origin.getBalance() < ammount){
                return new ResponseResult(false, "Insuficcient balance");
            }

            origin.setBalance(origin.getBalance() - ammount);
            repository.save(origin);
            return new ResponseResult(true, "Withdraw sucessful");
        }
        return new ResponseResult(false, "Invalid ammount");
    }


    public ResponseResult attemptTransaction(String requester, TransactionDto transactionParameters){
        User origin = repository.findByName(requester);
        User destinatary;
        if (transactionParameters.getAmmount() <= 0){
            return new ResponseResult(false, "Invalid ammount");
        }

        try {
            destinatary = repository.findByNameOrCpf(transactionParameters.getName(), transactionParameters.getCpf());
        }catch (NullPointerException e){
            return new ResponseResult(false, "Invalid destinatary");
        }

        if(getOwnBalance(requester) < transactionParameters.getAmmount()){
            return new ResponseResult(false, "Insuficient balance.");
        }

        origin.setBalance(origin.getBalance() - transactionParameters.getAmmount());
        destinatary.setBalance(destinatary.getBalance()+transactionParameters.getAmmount());
        repository.save(origin);
        repository.save(destinatary);
        return new ResponseResult(true, "Transaction successful");
    }
}