package org.example.modelo;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CriteriosEva {
    private String caracteristica;
    private String descripcion;
    private BigDecimal ponderacion;

}
