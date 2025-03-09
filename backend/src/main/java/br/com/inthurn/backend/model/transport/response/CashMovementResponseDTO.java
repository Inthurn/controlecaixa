package br.com.inthurn.backend.model.transport.response;

import br.com.inthurn.backend.enums.CashMovementType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CashMovementResponseDTO(String id, LocalDate date, CashMovementType type, String description,
                                      BigDecimal value, String cashBalanceId) {
}
