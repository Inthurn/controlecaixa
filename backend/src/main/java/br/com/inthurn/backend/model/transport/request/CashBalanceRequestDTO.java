package br.com.inthurn.backend.model.transport.request;

import java.math.BigDecimal;

public record CashBalanceRequestDTO(String id, String description, BigDecimal initialBalance) {
}
