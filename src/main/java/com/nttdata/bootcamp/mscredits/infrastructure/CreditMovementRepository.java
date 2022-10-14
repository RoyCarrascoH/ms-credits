package com.nttdata.bootcamp.mscredits.infrastructure;

import com.nttdata.bootcamp.mscredits.model.CreditMovement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CreditMovementRepository extends ReactiveMongoRepository<CreditMovement, String> {

}