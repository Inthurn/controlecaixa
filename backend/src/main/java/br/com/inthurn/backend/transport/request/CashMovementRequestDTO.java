package br.com.inthurn.backend.transport.request;

public record CashMovementRequestDTO(String id, String type, String description, Double value, String cashBalanceId) {
}
