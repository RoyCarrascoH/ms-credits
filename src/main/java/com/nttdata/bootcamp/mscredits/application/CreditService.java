package com.nttdata.bootcamp.mscredits.application;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.nttdata.bootcamp.mscredits.model.Credit;

public interface CreditService {

    public Flux<Credit> findAll();

    public Mono<Credit> findById(String idCredit);

    public Mono<Credit> save(Credit credit);

    public Mono<Credit> update(Credit credit, String idCredit);

    public Mono<Void> delete(String idCredit);
}
