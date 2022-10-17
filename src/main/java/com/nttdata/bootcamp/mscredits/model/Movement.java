package com.nttdata.bootcamp.mscredits.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movement {

    @Id
    private String idMovement;
    private String accountNumber;
    private Integer numberMovement;
    private String movementType;
    private Double amount;
    private Double balance;
    private String currency;
    private Date movementDate;
    private Credit credit;

}

