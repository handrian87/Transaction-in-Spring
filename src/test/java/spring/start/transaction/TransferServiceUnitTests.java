package spring.start.transaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.start.transaction.Model.Account;
import spring.start.transaction.Repo.AccountRepository;
import spring.start.transaction.Service.TransferService;

import java.math.BigDecimal;
import java.util.Optional;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class TransferServiceUnitTests {
    @Test
    @DisplayName("Test the amount is transferred from one account to another account" +
            "if no exception occurs.")
    public void moneyTransferHappyFlow(){
        AccountRepository accountRepository = mock(AccountRepository.class);
        TransferService transferService = new TransferService(accountRepository);
        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal(1000));

        Account destination = new Account();
        destination.setId(2);
        destination.setAmount(new BigDecimal(1000));

        given(accountRepository.findAccountById(sender.getId()))
                .willReturn(sender);
        given(accountRepository.findAccountById(destination.getId()))
                .willReturn(destination);

        transferService.transferMoney(
                sender.getId(),
                destination.getId(),
                new BigDecimal(100)
        );
        verify(accountRepository)
                .changeAmount(1, new BigDecimal(900));
        verify(accountRepository)
                .changeAmount(2, new BigDecimal(1100));
    }
}
