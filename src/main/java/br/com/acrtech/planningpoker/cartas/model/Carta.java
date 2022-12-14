package br.com.acrtech.planningpoker.cartas.model;

import br.com.acrtech.planningpoker.cartas.dto.CartaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "cartas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Carta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Float esforco;
    private String valor;
    @Enumerated(EnumType.STRING)
    private Organizacao organizacao;

    public Carta(CartaDto carta) {
        BeanUtils.copyProperties(carta, this);
    }
}
