package com.nttdata.bootcamp.mscredits.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "Credit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    @Id
    private String idCredit;

    @NotNull(message = "no debe estar nulo")
    private Integer creditNumber;

    @NotEmpty(message = "no debe estar vacío")
    private String creditType;

    @NotNull(message = "no debe estar nulo")
    private Double amountCredit;

    @NotEmpty(message = "no debe estar vacío")
    private String currency;

    private Integer maximumCreditQuantity;

    @NotNull(message = "no debe estar nulo")
    private Integer numberQuotas;

    @NotNull(message = "no debe estar nulo")
    private Boolean creditCard;

    private Integer cardNumber;

    private Double creditLineAmount;

}
