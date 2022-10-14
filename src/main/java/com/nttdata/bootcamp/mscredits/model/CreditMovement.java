package com.nttdata.bootcamp.mscredits.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "CreditMovement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditMovement {

    @Id
    private String idCreditMovement;

    @NotNull(message = "no debe estar nulo")
    @NotEmpty(message = "no debe estar vacío")
    private String idCredit; //PENDIENTE ESTABLECER RELACION

    @NotEmpty(message = "no debe estar vacío")
    private String movementCreditType;

    @NotNull(message = "no debe estar nulo")
    private Double amount;

    @NotNull(message = "no debe estar nulo")
    private Double balance;

}
