package br.com.acrtech.planningpoker.cartas.repository;

import br.com.acrtech.planningpoker.cartas.model.Carta;
import br.com.acrtech.planningpoker.cartas.model.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaRepository extends JpaRepository<Carta, Long> {

    List<Carta> findAllByOrganizacao(Organizacao organizacao);

    void deleteCartasByOrganizacao(Organizacao organizacao);
}
