package br.com.inthurn.backend.model.transport.response;

import java.time.LocalDate;

public record CashMovementResponseDTO(String id, LocalDate date, String type, String description,
                                      Double value, String cashBalanceId) {
}
