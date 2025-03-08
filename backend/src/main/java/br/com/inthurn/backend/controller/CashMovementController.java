package br.com.inthurn.backend.controller;

import br.com.inthurn.backend.service.CashMovementService;
import br.com.inthurn.backend.transport.request.CashMovementRequestDTO;
import br.com.inthurn.backend.transport.response.CashMovementResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cash-movement")
@AllArgsConstructor
public class CashMovementController {

    private final CashMovementService CASH_MOVEMENT_SERVICE;

    @GetMapping
    public ResponseEntity<List<CashMovementResponseDTO>> getCashMovementsByCashBalance(String id){
        return new ResponseEntity<>(CASH_MOVEMENT_SERVICE.getCashMovementsByCashBalance(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addCashMovement(@RequestBody CashMovementRequestDTO cashMovementRequestDTO){
        CASH_MOVEMENT_SERVICE.addCashMovement(cashMovementRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
