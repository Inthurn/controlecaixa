package br.com.inthurn.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "CashBalance")
@Table(name = "cashBalances")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CashBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String description;

    @Column(nullable = false)
    private Double initialBalance;

    @OneToMany(mappedBy = "cashBalance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CashMovement> cashMovements;

    @PrePersist
    public void prePersist() {
        if (this.initialBalance == null) this.initialBalance = 0.0;
    }
}
