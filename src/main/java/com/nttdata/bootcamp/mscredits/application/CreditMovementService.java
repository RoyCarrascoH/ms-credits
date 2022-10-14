package com.nttdata.bootcamp.mscredits.application;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.nttdata.bootcamp.mscredits.model.CreditMovement;

public interface CreditMovementService {

    public Flux<CreditMovement> findAll();

    public Mono<CreditMovement> findById(String idCreditMovement);

    public Mono<CreditMovement> save(CreditMovement creditMovement);

    public Mono<CreditMovement> update(CreditMovement creditMovement, String idCreditMovement);

    public Mono<Void> delete(String idCreditMovement);
}
