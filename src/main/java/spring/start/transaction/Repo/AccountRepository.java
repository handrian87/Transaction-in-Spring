package spring.start.transaction.Repo;

import spring.start.transaction.Model.Account;
import spring.start.transaction.Model.AccountRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbc;
    public AccountRepository(JdbcTemplate jdbc){
        this.jdbc= jdbc;
    }

    public Account findAccountById(long id){
        String sql = "SELECT * FROM account WHERE id=?";
        return jdbc.queryForObject(sql, new AccountRowMapper(), id);
    }
    public void changeAmount(long id, BigDecimal amount){
        String sql = "UPDATE account SET amount =? WHERE id =?";
        jdbc.update(sql,amount, id);
    }
    public List<Account> findAllAccounts(){
        String sql = "SELECT * FROM account";
        RowMapper<Account>accountRowMapper = (r,i) -> {
            Account a = new Account();
            a.setId(r.getInt("id"));
            a.setName(r.getString("name"));
            a.setAmount(r.getBigDecimal("amount"));
            return a;
        };
        return jdbc.query(sql,accountRowMapper);
        //return jdbc.query(sql, new AccountRowMapper());
    }
}
