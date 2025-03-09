package br.com.inthurn.backend.model.transport.response;

import java.math.BigDecimal;

public record CashBalanceResponseDTO (String id, String description, BigDecimal initialValue){
}
