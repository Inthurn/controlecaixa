package br.com.inthurn.backend.service;

import br.com.inthurn.backend.exceptions.ResourceNotFoundException;
import br.com.inthurn.backend.model.entity.CashMovement;
import br.com.inthurn.backend.repository.CashMovementRepository;
import br.com.inthurn.backend.model.transport.request.CashMovementRequestDTO;
import br.com.inthurn.backend.model.transport.response.CashMovementResponseDTO;
import br.com.inthurn.backend.utils.IdEncryptor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@AllArgsConstructor
public class CashMovementService {

    private final CashMovementRepository CASH_MOVEMENT_REPOSITORY;
    private final CashBalanceService CASH_BALANCE_SERVICE;

    public List<CashMovementResponseDTO> getCashMovementsByCashBalance(String cashBalanceId) {
        try {
            return CASH_MOVEMENT_REPOSITORY.findByCashBalanceId(IdEncryptor.decryptId(cashBalanceId))
                    .stream().map(this::convertModelToDTO)
                    .toList();

        } catch (Exception e) {
            throw new ResourceNotFoundException("Não foi possível consultar as movimentações dos caixas");
        }
    }

    public void addCashMovement(CashMovementRequestDTO cashMovementRequestDTO) {
        try {
            CASH_MOVEMENT_REPOSITORY.save(convertDTOtoModel(cashMovementRequestDTO));
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criar movimentação de caixa: " + e.getMessage());
        }
    }

    public List<CashMovementResponseDTO> getCashMovementsByMonth(YearMonth yearMonth, String cashBalanceId) {
        try {
            return CASH_MOVEMENT_REPOSITORY
                    .findByPeriod(yearMonth.atDay(1), yearMonth.atEndOfMonth(), IdEncryptor.decryptId(cashBalanceId))
                    .stream()
                    .map(this::convertModelToDTO)
                    .toList();

        } catch (Exception e) {
            throw new ResourceNotFoundException("Não foi possível localizar as movimentações do período informado");
        }
    }

    private CashMovement convertDTOtoModel(CashMovementRequestDTO cashMovementRequestDTO) {
        return CashMovement.builder()
                .id(IdEncryptor.decryptId(cashMovementRequestDTO.id()))
                .description(cashMovementRequestDTO.description())
                .type(cashMovementRequestDTO.type())
                .date(cashMovementRequestDTO.date() != null ? cashMovementRequestDTO.date() : LocalDate.now())
                .cashBalance(CASH_BALANCE_SERVICE.getCashBalance(IdEncryptor.decryptId(cashMovementRequestDTO.cashBalanceId())))
                .value(cashMovementRequestDTO.value())
                .build();
    }

    private CashMovementResponseDTO convertModelToDTO(CashMovement cashMovement) {
        return new CashMovementResponseDTO(IdEncryptor.encryptId(cashMovement.getId()),
                cashMovement.getDate(),
                cashMovement.getType(),
                cashMovement.getDescription(),
                cashMovement.getValue(),
                IdEncryptor.encryptId(cashMovement.getCashBalance().getId()));
    }

}
