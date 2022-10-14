package com.nttdata.bootcamp.mscredits.controller;

import java.net.URI;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.nttdata.bootcamp.mscredits.application.CreditService;
import com.nttdata.bootcamp.mscredits.model.Credit;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/credits")
public class CreditController {
    @Autowired
    private CreditService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Credit>>> listCredits() {
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(service.findAll()));
    }

    @GetMapping("/{idCredit}")
    public Mono<ResponseEntity<Credit>> viewCreditDetails(@PathVariable("idCredit") String idCredit) {
        return service.findById(idCredit).map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> saveCredit(@Valid @RequestBody Mono<Credit> monoCredit) {
        Map<String, Object> request = new HashMap<>();
        return monoCredit.flatMap(credit -> {
            return service.save(credit).map(c -> {
                request.put("Credito", c);
                request.put("mensaje", "Credito guardado con exito");
                request.put("timestamp", new Date());
                return ResponseEntity.created(URI.create("/api/credits/".concat(c.getIdCredit())))
                        .contentType(MediaType.APPLICATION_JSON_UTF8).body(request);
            });
        });
    }

    @PutMapping("/{idCredit}")
    public Mono<ResponseEntity<Credit>> editCredit(@Valid @RequestBody Credit credit, @PathVariable("idCredit") String idCredit) {
        return service.update(credit, idCredit)
                .map(c -> ResponseEntity.created(URI.create("/api/credits/".concat(idCredit)))
                        .contentType(MediaType.APPLICATION_JSON_UTF8).body(c));
    }

    @DeleteMapping("/{idCredit}")
    public Mono<ResponseEntity<Void>> deleteCredit(@PathVariable("idCredit") String idCredit) {
        return service.delete(idCredit).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }

}