package br.com.acrtech.planningpoker.cartas.repository;

import br.com.acrtech.planningpoker.cartas.model.Carta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartaRepository extends JpaRepository<Carta, UUID> {

    List<Carta> findAllByIdOrganizacao(Integer organizacao);

    void deleteCartasByIdOrganizacao(Integer organizacao);
}
