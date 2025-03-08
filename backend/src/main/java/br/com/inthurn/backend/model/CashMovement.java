package br.com.inthurn.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
    private Date date;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name = "cashMovements", nullable = false)
    private CashBalance cashBalance;

    @PrePersist
    public void prePersist() {
        if (this.value == null) this.value = 0.0;
    }
}
