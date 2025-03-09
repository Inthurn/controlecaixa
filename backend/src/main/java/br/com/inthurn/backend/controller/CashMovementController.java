package br.com.inthurn.backend.controller;

import br.com.inthurn.backend.service.CashMovementService;
import br.com.inthurn.backend.model.transport.request.CashMovementRequestDTO;
import br.com.inthurn.backend.model.transport.response.CashMovementResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/cash-movement")
@AllArgsConstructor
public class CashMovementController {

    private final CashMovementService CASH_MOVEMENT_SERVICE;

    @GetMapping("/{id}")
    public ResponseEntity<List<CashMovementResponseDTO>> getCashMovementsByCashBalance(@PathVariable("id") String id){
        return new ResponseEntity<>(CASH_MOVEMENT_SERVICE.getCashMovementsByCashBalance(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addCashMovement(@RequestBody CashMovementRequestDTO cashMovementRequestDTO){
        CASH_MOVEMENT_SERVICE.addCashMovement(cashMovementRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/movements/{year-month}")
    public ResponseEntity<List<CashMovementResponseDTO>> getCashMovementsByMonth(@PathVariable("year-month") YearMonth yearMonth,
                                                                                 @RequestParam("cash-balance") String cashBalanceId){
        return new ResponseEntity<>(CASH_MOVEMENT_SERVICE.getCashMovementsByMonth(yearMonth, cashBalanceId), HttpStatus.OK);
    }

}
