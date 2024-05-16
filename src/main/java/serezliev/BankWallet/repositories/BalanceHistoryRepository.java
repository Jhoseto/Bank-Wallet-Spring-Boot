package serezliev.BankWallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serezliev.BankWallet.model.BalanceHistoryEntity;
import serezliev.BankWallet.model.UserEntity;

import java.util.List;

public interface BalanceHistoryRepository extends JpaRepository<BalanceHistoryEntity, Long> {

    List<BalanceHistoryEntity> findByUser(UserEntity user);

}
