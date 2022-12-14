package br.com.acrtech.planningpoker.cartas.dto;

import br.com.acrtech.planningpoker.cartas.model.Carta;
import br.com.acrtech.planningpoker.cartas.model.Organizacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor
public class CartaDto implements Serializable {
    private Long id;
    @NotNull @Positive(message = "Esforço não pode ser negativo")
    private Float esforco;
    @NotNull
    private String valor;
    @NotNull
    private Organizacao organizacao;

    public CartaDto(Carta carta) {
        BeanUtils.copyProperties(carta, this);
    }
}
