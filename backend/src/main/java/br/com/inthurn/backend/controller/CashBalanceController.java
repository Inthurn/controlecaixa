package br.com.inthurn.backend.controller;

import br.com.inthurn.backend.service.CashBalanceService;
import br.com.inthurn.backend.transport.request.CashBalanceRequestDTO;
import br.com.inthurn.backend.transport.response.CashBalanceResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cash-balance")
public class CashBalanceController {

    private final CashBalanceService CASH_BALANCE_SERVICE;

    public CashBalanceController(CashBalanceService CASH_BALANCE_SERVICE) {
        this.CASH_BALANCE_SERVICE = CASH_BALANCE_SERVICE;
    }

    @GetMapping
    public ResponseEntity<List<CashBalanceResponseDTO>> getCashBalances() throws Exception {
        List<CashBalanceResponseDTO> cashBalanceResponseDTOList = CASH_BALANCE_SERVICE.getCashBalances();
        return ResponseEntity.ok(cashBalanceResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashBalanceResponseDTO> getCashBalance(@PathVariable("id") String id){
        return ResponseEntity.ok(CASH_BALANCE_SERVICE.getCashBalance(id));
    }

    @PostMapping
    public ResponseEntity<Void> addCashBalance(@RequestBody CashBalanceRequestDTO cashBalanceRequestDTO){
        CASH_BALANCE_SERVICE.addCashBalance(cashBalanceRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateCashBalance(@RequestBody CashBalanceRequestDTO cashBalanceRequestDTO) {
        CASH_BALANCE_SERVICE.updateCashBalance(cashBalanceRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashBalance(@PathVariable("id") String id){
       CASH_BALANCE_SERVICE.deleteCashBalance(id);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
