package com.nttdata.bootcamp.mscredits.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private String idClient;

    @NotNull(message = "no debe estar nulo")
    private Integer creditNumber;

    @NotEmpty(message = "no debe estar vacío")
    private String creditType;

    @NotEmpty(message = "no debe estar vacío")
    private Double creditLineAmount;

    @NotEmpty(message = "no debe estar vacío")
    private String currency;

    private Double creditAvailable;

    private Boolean status;

}
