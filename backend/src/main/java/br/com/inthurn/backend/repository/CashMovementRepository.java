package br.com.inthurn.backend.repository;

import br.com.inthurn.backend.model.entity.CashMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CashMovementRepository extends JpaRepository<CashMovement, Long> {
    List<CashMovement> findByCashBalanceId(Long cashBalanceId);

    @Query("SELECT m " +
            "FROM CashMovement m " +
            "WHERE m.date BETWEEN :start AND :end " +
            "AND m.cashBalance.id = :cashBalanceId")
    List<CashMovement> findByPeriod(@Param("start") LocalDate start,
                                    @Param("end") LocalDate end,
                                    @Param("cashBalanceId") Long cashBalanceId);
}
