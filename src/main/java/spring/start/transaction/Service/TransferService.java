package spring.start.transaction.Service;

import spring.start.transaction.Model.Account;
import spring.start.transaction.Repo.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TransferService {
    private final AccountRepository accountRepository;
    public TransferService(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }

    public void transferMoney(long idSender, long idReceveir, BigDecimal amount){
        Account sender = accountRepository.findAccountById(idSender);
        Account receiver = accountRepository.findAccountById(idReceveir);
        BigDecimal senderNewAmount = sender.getAmount().subtract(amount);
        BigDecimal receiveNewAmount = receiver.getAmount().add(amount);
        accountRepository.changeAmount(idSender,senderNewAmount);
        accountRepository.changeAmount(idReceveir, receiveNewAmount);

        //throw new RuntimeException("Testing retrieving data to the sender after getting an exception");
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAllAccounts();
    }
}
