package br.com.inthurn.backend.transport.response;

import java.sql.Date;

public record CashMovementResponseDTO(String id, Date date, String type, String description,
                                      Double value, String cashBalanceId) {
}
