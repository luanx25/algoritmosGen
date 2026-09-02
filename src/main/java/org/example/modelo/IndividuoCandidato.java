package org.example.modelo;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndividuoCandidato {
    private String cromosoma;
    private double fitness;
    private String interpretacion;
    private String categoria;

}
