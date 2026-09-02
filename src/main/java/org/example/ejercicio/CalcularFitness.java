package org.example.ejercicio;

import org.example.modelo.CriteriosEva;
import org.example.modelo.IndividuoCandidato;

import java.math.BigDecimal;
import java.util.List;


public class CalcularFitness {

    private List<CriteriosEva> criteriosEvaluacion = List.of(
            CriteriosEva.builder()
                    .caracteristica("Dominio de IA / ML")
                    .descripcion("Conocimientos en modelos ML, redes neuronales, SVM, etc")
                    .ponderacion(new BigDecimal("0.25"))
                    .build(),
            CriteriosEva.builder()
                    .caracteristica("Programacion avanzada")
                    .descripcion("Capacidad")
                    .ponderacion(new BigDecimal("0.25"))
                    .build(),
            CriteriosEva.builder()
                    .caracteristica("Dominio de IA / ML")
                    .descripcion("Conocimientos en modelos ML, redes neuronales, SVM, etc")
                    .ponderacion(new BigDecimal("0.25"))
                    .build(),
            CriteriosEva.builder()
                    .caracteristica("Dominio de IA / ML")
                    .descripcion("Conocimientos en modelos ML, redes neuronales, SVM, etc")
                    .ponderacion(new BigDecimal("0.25"))
                    .build(),
            CriteriosEva.builder()
                    .caracteristica("Dominio de IA / ML")
                    .descripcion("Conocimientos en modelos ML, redes neuronales, SVM, etc")
                    .ponderacion(new BigDecimal("0.25"))
                    .build(),
            CriteriosEva.builder()
                    .caracteristica("Dominio de IA / ML")
                    .descripcion("Conocimientos en modelos ML, redes neuronales, SVM, etc")
                    .ponderacion(new BigDecimal("0.25"))
                    .build(),
            CriteriosEva.builder()
                    .caracteristica("Dominio de IA / ML")
                    .descripcion("Conocimientos en modelos ML, redes neuronales, SVM, etc")
                    .ponderacion(new BigDecimal("0.25"))
                    .build()
            );


    public IndividuoCandidato calcularFitness(String cromosoma) {


    }
}
