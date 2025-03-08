package br.com.inthurn.backend.service;

import br.com.inthurn.backend.model.CashBalance;
import br.com.inthurn.backend.repository.CashBalanceRepository;
import br.com.inthurn.backend.transport.request.CashBalanceRequestDTO;
import br.com.inthurn.backend.transport.response.CashBalanceResponseDTO;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class CashBalanceService {

    private final CashBalanceRepository CASH_BALANCE_REPOSITORY;

    public CashBalanceService(CashBalanceRepository CASH_BALANCE_REPOSITORY) {
        this.CASH_BALANCE_REPOSITORY = CASH_BALANCE_REPOSITORY;
    }

    public List<CashBalanceResponseDTO> getCashBalances() throws Exception{
        try {
            List<CashBalance> cashBalances = CASH_BALANCE_REPOSITORY.findAll();
            return cashBalances.stream().map(this::convertModelToDTO).toList();
        }catch (Exception e){
            throw new Exception("Não foi possível consultar os caixas", e.getCause());
        }
    }

    public CashBalanceResponseDTO getCashBalance(String id){
        Optional<CashBalance> cashBalance = CASH_BALANCE_REPOSITORY.findById(decryptId(id));

        return cashBalance.map(this::convertModelToDTO).orElseGet(() -> new CashBalanceResponseDTO(null, null, null));
    }

    public void addCashBalance(CashBalanceRequestDTO cashBalanceRequestDTO) {
        try {
            CashBalance cashBalance = convertDTOtoModel(cashBalanceRequestDTO);
            CASH_BALANCE_REPOSITORY.save(cashBalance);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criar caixa.", e.getCause());
        }
    }

    private String encryptId(Long id) {
        return Base64.getEncoder().encodeToString(id.toString().getBytes());
    }

    public Long decryptId(String encryptedId){
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedId);
        String id = new String(decodedBytes, StandardCharsets.UTF_8);
        return Long.parseLong(id);
    }

    private CashBalance convertDTOtoModel(CashBalanceRequestDTO cashBalanceRequestDTO) {
        return CashBalance.builder()
                .id(decryptId(cashBalanceRequestDTO.id()))
                .description(cashBalanceRequestDTO.description())
                .initialBalance(cashBalanceRequestDTO.initialValue())
                .build();
    }

    private CashBalanceResponseDTO convertModelToDTO(CashBalance cashBalance) {
        return new CashBalanceResponseDTO(this.encryptId(cashBalance.getId()),
                cashBalance.getDescription(),
                cashBalance.getInitialBalance());
    }

    public void updateCashBalance(CashBalanceRequestDTO cashBalanceRequestDTO) {
        try {
            CashBalance cashBalance = convertDTOtoModel(cashBalanceRequestDTO);
            CASH_BALANCE_REPOSITORY.save(cashBalance);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível atualizar caixa", e.getCause());
        }
    }

    public void deleteCashBalance(String id) {
        try {
            Optional<CashBalance> cashBalance = CASH_BALANCE_REPOSITORY.findById(decryptId(id));

            cashBalance.ifPresent(CASH_BALANCE_REPOSITORY::delete);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível atualizar caixa", e.getCause());
        }
    }
}
