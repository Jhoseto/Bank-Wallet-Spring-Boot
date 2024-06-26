package serezliev.BankWallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import serezliev.BankWallet.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
}
