package com.nttdata.bootcamp.mscredits.application;

import com.nttdata.bootcamp.mscredits.exception.ResourceNotFoundException;
import com.nttdata.bootcamp.mscredits.infrastructure.CreditMovementRepository;
import com.nttdata.bootcamp.mscredits.model.CreditMovement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditMovementServiceImpl implements CreditMovementService {

    @Autowired
    private CreditMovementRepository creditMovementRepository;

    @Override
    public Flux<CreditMovement> findAll() {
        return creditMovementRepository.findAll();
    }

    @Override
    public Mono<CreditMovement> findById(String idMovementCredit) {
        return Mono.just(idMovementCredit)
                .flatMap(creditMovementRepository::findById)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("MovementCredit", "IdMovementCredit", idMovementCredit)));
    }

    @Override
    public Mono<CreditMovement> save(CreditMovement creditMovement) {
        return creditMovementRepository.save(creditMovement);
    }

    @Override
    public Mono<CreditMovement> update(CreditMovement creditMovement, String idCreditMovement) {
        return creditMovementRepository.findById(idCreditMovement)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("CreditMovement", "IdCreditMovement", idCreditMovement)))
                .flatMap(c -> {
                    c.setIdCredit(creditMovement.getIdCredit());
                    c.setMovementCreditType(creditMovement.getMovementCreditType());
                    c.setAmount(creditMovement.getAmount());
                    c.setBalance(creditMovement.getBalance());
                    return creditMovementRepository.save(c);
                });
    }

    @Override
    public Mono<Void> delete(String idCreditMovement) {
        return creditMovementRepository.findById(idCreditMovement)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("CreditMovement", "IdCreditMovement", idCreditMovement)))
                .flatMap(creditMovementRepository::delete);
    }

}
