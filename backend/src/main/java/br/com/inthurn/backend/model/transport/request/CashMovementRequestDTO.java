package br.com.inthurn.backend.model.transport.request;

import java.sql.Date;
import java.time.LocalDate;

public record CashMovementRequestDTO(String id, String type, String description, LocalDate date, Double value, String cashBalanceId) {
}
