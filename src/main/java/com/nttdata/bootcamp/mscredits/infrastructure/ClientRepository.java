package com.nttdata.bootcamp.mscredits.infrastructure;

import com.nttdata.bootcamp.mscredits.config.WebClientConfig;
import com.nttdata.bootcamp.mscredits.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ClientRepository {
    @Autowired
    ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

    public Mono<Client> findClientByDni(String documentNumber) {
        WebClientConfig webconfig = new WebClientConfig();
        return webconfig.setUriData("http://localhost:8080")
                .flatMap(d -> webconfig.getWebclient().get().uri("/api/clients/documentNumber/" + documentNumber).retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new Exception("Error 400")))
                        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new Exception("Error 500")))
                        .bodyToMono(Client.class)
                        .transform(it -> reactiveCircuitBreakerFactory.create("parameter-service").run(it, throwable -> Mono.just(new Client())))
                );
    }
}
