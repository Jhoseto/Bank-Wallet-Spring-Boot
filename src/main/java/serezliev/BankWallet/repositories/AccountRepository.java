package serezliev.BankWallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serezliev.BankWallet.Model.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {


}