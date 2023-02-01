package br.com.acrtech.planningpoker.cartas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class OrganizacaoDto {
    private Integer id;
    private String nome;
}
