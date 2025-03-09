package br.com.inthurn.backend.repository;

import br.com.inthurn.backend.model.entity.CashBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashBalanceRepository extends JpaRepository<CashBalance, Long> {
}
