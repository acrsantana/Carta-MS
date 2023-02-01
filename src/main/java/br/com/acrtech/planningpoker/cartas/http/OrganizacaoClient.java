package br.com.acrtech.planningpoker.cartas.http;

import br.com.acrtech.planningpoker.cartas.dto.OrganizacaoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "organizacoes", url = "http://localhost:8080/organizacoes/planningpoker/api/v1/organizacoes")
public interface OrganizacaoClient {
    @GetMapping(value = "/{id}")
    OrganizacaoDto findById(@PathVariable Integer id);

    @GetMapping
    List<OrganizacaoDto> findAll();
}
