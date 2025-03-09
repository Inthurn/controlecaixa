package br.com.inthurn.backend.model.transport.request;

import br.com.inthurn.backend.enums.CashMovementType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CashMovementRequestDTO(String id, CashMovementType type, String description, LocalDate date,
                                     BigDecimal value, String cashBalanceId) {
}
