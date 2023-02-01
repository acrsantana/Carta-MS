package br.com.acrtech.planningpoker.cartas.dto;

import br.com.acrtech.planningpoker.cartas.model.Carta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor
public class CartaDto implements Serializable {
    private UUID id;
    @NotNull @Positive(message = "Esforço não pode ser negativo")
    private Float esforco;
    @NotNull
    private String valor;
    @NotNull
    private Integer idOrganizacao;
    private String organizacao;

    public CartaDto(Carta carta) {
        BeanUtils.copyProperties(carta, this);
    }
}
