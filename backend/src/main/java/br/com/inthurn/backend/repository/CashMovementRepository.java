package br.com.inthurn.backend.repository;

import br.com.inthurn.backend.model.CashMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashMovementRepository extends JpaRepository<CashMovement, Long> {
    List<CashMovement> findByCashBalanceId(Long cashBalanceId);
}
