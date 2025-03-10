package br.com.inthurn.backend.model.entity;

import br.com.inthurn.backend.enums.CashMovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Entity(name = "CashMovement")
@Table(name = "CashMovements")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CashMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CashMovementType type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "cashbalance_id", nullable = false)
    private CashBalance cashBalance;

    @PrePersist
    public void prePersist() {
        if (this.value == null) this.value = BigDecimal.ZERO;
    }
}
