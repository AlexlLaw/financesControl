package com.api.financescontrol.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

@JsonInclude(Include.NON_NULL)
public class ConsultaPaginada<T> implements Serializable {
    private static final long serialVersionUID = -3504129273664321753L;
    private List<T> resultados;
    private int numeroPagina;
    private int tamanhoPagina;
    private long quantidadeTotalResultados;

    @JsonCreator(
            mode = Mode.PROPERTIES
    )
    public ConsultaPaginada(@JsonProperty("resultados") List<T> resultados, @JsonProperty("numeroPagina") int numeroPagina, @JsonProperty("tamanhoPagina") int tamanhoPagina, @JsonProperty("quantidadeTotalResultados") long quantidadeTotalResultados) {
        this.resultados = resultados;
        this.numeroPagina = numeroPagina;
        this.tamanhoPagina = tamanhoPagina;
        this.quantidadeTotalResultados = quantidadeTotalResultados;
    }

    public ConsultaPaginada(List<T> resultados) {
        this.resultados = resultados;
    }

    public ConsultaPaginada() {
        this(new ArrayList());
    }

    public static <T> ConsultaPaginada<T> of(List<T> resultados, int numeroPagina, int tamanhoPagina, long quantidadeTotalResultados) {
        return new ConsultaPaginada(resultados, numeroPagina, tamanhoPagina, quantidadeTotalResultados);
    }


    @Generated
    public List<T> getResultados() {
        return this.resultados;
    }


    @Generated
    public int getNumeroPagina() {
        return this.numeroPagina;
    }


    @Generated
    public int getTamanhoPagina() {
        return this.tamanhoPagina;
    }


    @Generated
    public long getQuantidadeTotalResultados() {
        return this.quantidadeTotalResultados;
    }


    @Generated
    public void setResultados(List<T> resultados) {
        this.resultados = resultados;
    }


    @Generated
    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }


    @Generated
    public void setTamanhoPagina(int tamanhoPagina) {
        this.tamanhoPagina = tamanhoPagina;
    }


    @Generated
    public void setQuantidadeTotalResultados(long quantidadeTotalResultados) {
        this.quantidadeTotalResultados = quantidadeTotalResultados;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ConsultaPaginada)) {
            return false;
        } else {
            ConsultaPaginada<?> other = (ConsultaPaginada)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getNumeroPagina() != other.getNumeroPagina()) {
                return false;
            } else if (this.getTamanhoPagina() != other.getTamanhoPagina()) {
                return false;
            } else if (this.getQuantidadeTotalResultados() != other.getQuantidadeTotalResultados()) {
                return false;
            } else {
                Object this$resultados = this.getResultados();
                Object other$resultados = other.getResultados();
                if (this$resultados == null) {
                    if (other$resultados != null) {
                        return false;
                    }
                } else if (!this$resultados.equals(other$resultados)) {
                    return false;
                }

                return true;
            }
        }
    }


    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof ConsultaPaginada;
    }


    @Generated
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + this.getNumeroPagina();
        result = result * 59 + this.getTamanhoPagina();
        long $quantidadeTotalResultados = this.getQuantidadeTotalResultados();
        result = result * 59 + (int)($quantidadeTotalResultados >>> 32 ^ $quantidadeTotalResultados);
        Object $resultados = this.getResultados();
        result = result * 59 + ($resultados == null ? 43 : $resultados.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        List var10000 = this.getResultados();
        return "ConsultaPaginada(resultados=" + var10000 + ", numeroPagina=" + this.getNumeroPagina() + ", tamanhoPagina=" + this.getTamanhoPagina() + ", quantidadeTotalResultados=" + this.getQuantidadeTotalResultados() + ")";
    }
}
