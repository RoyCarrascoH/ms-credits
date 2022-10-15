package com.nttdata.bootcamp.mscredits.application;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import com.nttdata.bootcamp.mscredits.model.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import com.nttdata.bootcamp.mscredits.infrastructure.CreditRepository;
import com.nttdata.bootcamp.mscredits.exception.ResourceNotFoundException;

@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private CreditRepository creditRepository;

    @Override
    public Flux<Credit> findAll() {
        return creditRepository.findAll();
    }

    @Override
    public Mono<Credit> findById(String idCredit) {
        return Mono.just(idCredit)
                .flatMap(creditRepository::findById)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Credito", "IdCredito", idCredit)));
    }

    @Override
    public Mono<Credit> save(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public Mono<Credit> update(Credit credit, String idCredit) {
        return creditRepository.findById(idCredit)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Credit", "IdCredito", idCredit)))
                .flatMap(c -> {
                    c.setIdClient(credit.getIdClient());
                    c.setCreditNumber(credit.getCreditNumber());
                    c.setCreditType(credit.getCreditType());
                    c.setCreditLineAmount(credit.getCreditLineAmount());
                    c.setCurrency(credit.getCurrency());
                    c.setCreditAvailable(credit.getCreditAvailable());
                    c.setStatus(credit.getStatus());
                    return creditRepository.save(c);
                });
    }

    @Override
    public Mono<Void> delete(String idCredit) {
        return creditRepository.findById(idCredit)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Credito", "IdCredito", idCredit)))
                .flatMap(creditRepository::delete);
    }

}
