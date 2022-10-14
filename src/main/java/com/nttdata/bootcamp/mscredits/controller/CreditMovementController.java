package com.nttdata.bootcamp.mscredits.controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.nttdata.bootcamp.mscredits.application.CreditMovementService;
import com.nttdata.bootcamp.mscredits.model.CreditMovement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/creditsmovement")
public class CreditMovementController {
    @Autowired
    private CreditMovementService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<CreditMovement>>> listCreditsMovement() {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(service.findAll()));
    }

    @GetMapping("/{idCreditMovement}")
    public Mono<ResponseEntity<CreditMovement>> viewCreditMovementDetails(@PathVariable("idCreditMovement") String idCreditMovement) {
        return service.findById(idCreditMovement).map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> saveCreditMovement(@Valid @RequestBody Mono<CreditMovement> monoCreditMovement) {
        Map<String, Object> request = new HashMap<>();
        return monoCreditMovement.flatMap(creditMovement -> {
            return service.save(creditMovement).map(c -> {
                request.put("Credito", c);
                request.put("mensaje", "Movimiento de Credito guardado con exito");
                request.put("timestamp", new Date());
                return ResponseEntity.created(URI.create("/api/creditsmovement/".concat(c.getIdCreditMovement())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8).body(request);
            });
        });
    }

    @PutMapping("/{idCreditMovement}")
    public Mono<ResponseEntity<CreditMovement>> editCreditMovement(@Valid @RequestBody CreditMovement creditMovement, @PathVariable("idCreditMovement") String idCreditMovement) {
        return service.update(creditMovement, idCreditMovement)
                .map(c -> ResponseEntity.created(URI.create("/api/creditsmovement/".concat(idCreditMovement)))
                        .contentType(MediaType.APPLICATION_JSON_UTF8).body(c));
    }

    @DeleteMapping("/{idCreditMovement}")
    public Mono<ResponseEntity<Void>> deleteCreditMovement(@PathVariable("idCreditMovement") String idCreditMovement) {
        return service.delete(idCreditMovement).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }

}
