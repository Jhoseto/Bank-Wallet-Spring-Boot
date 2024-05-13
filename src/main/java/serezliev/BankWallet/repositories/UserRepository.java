package serezliev.BankWallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serezliev.BankWallet.Model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{


}
