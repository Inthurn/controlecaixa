package br.com.inthurn.backend.service;

import br.com.inthurn.backend.exceptions.ResourceNotFoundException;
import br.com.inthurn.backend.model.entity.CashBalance;
import br.com.inthurn.backend.repository.CashBalanceRepository;
import br.com.inthurn.backend.model.transport.request.CashBalanceRequestDTO;
import br.com.inthurn.backend.model.transport.response.CashBalanceResponseDTO;
import br.com.inthurn.backend.utils.IdEncryptor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CashBalanceService {

    private final CashBalanceRepository CASH_BALANCE_REPOSITORY;

    public List<CashBalanceResponseDTO> getCashBalances() {
        try {
            return CASH_BALANCE_REPOSITORY.findAll().stream().map(this::convertModelToDTO).toList();

        } catch (Exception e) {
            throw new ResourceNotFoundException("Não foi possível consultar os caixas.");
        }
    }

    public CashBalanceResponseDTO getCashBalance(String id) {
        try {
            return CASH_BALANCE_REPOSITORY.findById(IdEncryptor.decryptId(id))
                    .map(this::convertModelToDTO)
                    .orElseThrow(() -> new ResourceNotFoundException("Caixa não encontrado: " + id));

        } catch (Exception e) {
            throw new ResourceNotFoundException("Não foi possível consultar o caixa informado");
        }
    }

    public void addCashBalance(CashBalanceRequestDTO cashBalanceRequestDTO) {
        try {
            CashBalance cashBalance = convertDTOtoModel(cashBalanceRequestDTO);
            CASH_BALANCE_REPOSITORY.save(cashBalance);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criar caixa.");
        }
    }

    public void updateCashBalance(CashBalanceRequestDTO cashBalanceRequestDTO) {
        try {
            CashBalance cashBalance = convertDTOtoModel(cashBalanceRequestDTO);
            CASH_BALANCE_REPOSITORY.save(cashBalance);

        } catch (Exception e) {
            throw new RuntimeException("Não foi possível atualizar caixa.");
        }
    }

    public void deleteCashBalance(String id) {
        try {
            Optional<CashBalance> cashBalance = CASH_BALANCE_REPOSITORY.findById(IdEncryptor.decryptId(id));
            cashBalance.ifPresent(CASH_BALANCE_REPOSITORY::delete);

        } catch (Exception e) {
            throw new RuntimeException("Não foi possível excluir caixa " + id);
        }
    }

    private CashBalance convertDTOtoModel(CashBalanceRequestDTO cashBalanceRequestDTO) {
        return CashBalance.builder()
                .id(IdEncryptor.decryptId(cashBalanceRequestDTO.id()))
                .description(cashBalanceRequestDTO.description())
                .initialBalance(cashBalanceRequestDTO.initialBalance())
                .build();
    }

    private CashBalanceResponseDTO convertModelToDTO(CashBalance cashBalance) {
        return new CashBalanceResponseDTO(IdEncryptor.encryptId(cashBalance.getId()),
                cashBalance.getDescription(),
                cashBalance.getInitialBalance());
    }

    public CashBalance getCashBalance(Long id) {
        return CASH_BALANCE_REPOSITORY.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível consultar Caixa informado."));
    }

}
