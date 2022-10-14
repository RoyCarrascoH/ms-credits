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
    public Mono<Credit> findById(String idCredito) {
        return Mono.just(idCredito)
                .flatMap(creditRepository::findById)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Credito", "IdCredito", idCredito)));
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
                    c.setCreditNumber(credit.getCreditNumber());
                    c.setCreditType(credit.getCreditType());
                    c.setAmountCredit(credit.getAmountCredit());
                    c.setCurrency(credit.getCurrency());
                    c.setMaximumCreditQuantity(credit.getMaximumCreditQuantity());
                    c.setNumberQuotas(credit.getNumberQuotas());
                    c.setCreditCard(credit.getCreditCard());
                    c.setCardNumber(credit.getCardNumber());
                    c.setCreditLineAmount(credit.getCreditLineAmount());
                    return creditRepository.save(credit);
                });
    }

    @Override
    public Mono<Void> delete(String idCredit) {
        return creditRepository.findById(idCredit)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Credito", "IdCredito", idCredit)))
                .flatMap(creditRepository::delete);
    }

}
