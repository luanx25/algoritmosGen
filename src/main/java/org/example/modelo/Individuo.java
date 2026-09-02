package org.example.modelo;


public class Individuo implements Comparable<Individuo> {
    private String binario;
    private Double adaptado;

    public Individuo(String binario, Double adaptado) {
        this.binario = binario;
        this.adaptado = adaptado;
    }

    public String getBinario() {
        return binario;
    }

    public void setBinario(String binario) {
        this.binario = binario;
    }

    public Double getAdaptado() {
        return adaptado;
    }

    public void setAdaptado(Double adaptado) {
        this.adaptado = adaptado;
    }


    @Override
    public int compareTo(Individuo o) {
        return o.getAdaptado().compareTo(this.adaptado);
    }
    @Override
    public String toString() {

        return this.binario+" - "+this.adaptado;
    }
}
