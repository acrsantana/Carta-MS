package br.com.acrtech.planningpoker.cartas.service;

import br.com.acrtech.planningpoker.cartas.dto.CartaDto;
import br.com.acrtech.planningpoker.cartas.exception.CartaNaoEncontradaException;
import br.com.acrtech.planningpoker.cartas.model.Carta;
import br.com.acrtech.planningpoker.cartas.model.Organizacao;
import br.com.acrtech.planningpoker.cartas.repository.CartaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class CartaService {

    private final CartaRepository cartaRepository;

    public CartaService(CartaRepository cartaRepository) {
        this.cartaRepository = cartaRepository;
    }

    public List<CartaDto> findAll(){
        log.info("Buscando todas as cartas");
        return cartaRepository.findAll().stream().map(CartaDto::new).toList();
    }

    public CartaDto findById(Long id) {
        log.info("Buscando a carta com id {}", id);
        Optional<Carta> optionalCarta = cartaRepository.findById(id);
        if (optionalCarta.isEmpty()){
            log.error("Carta com o id {} não encontrada", id);
            throw new CartaNaoEncontradaException("Carta com o id " + id + " não encontrada");
        }
        return new CartaDto(optionalCarta.get());
    }

    public List<CartaDto> findAllByOrganizacao(Organizacao organizacao){
        log.info("Buscando todas as cartas da organização {}", organizacao.name());
        return cartaRepository.findAllByOrganizacao(organizacao).stream().map(CartaDto::new).toList();
    }

    public CartaDto save(CartaDto carta) {
        log.info("Salvando a carta {}", carta.getValor());
        return new CartaDto(cartaRepository.save(new Carta(carta)));
    }

    @Transactional
    public List<CartaDto> saveAll(List<CartaDto> cartas) {
        log.info("Salvando lista de cartas com {} cartas", cartas.size());
        List<Carta> list = cartaRepository.saveAll(cartas.stream().map(Carta::new).toList());
        return list.stream().map(CartaDto::new).toList();
    }

    public void delete(Long id) {
        log.info("Deletando a carta com id {}", id);
        cartaRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByOrganizacao(Organizacao organizacao) {
        log.info("Deletando todas as cartas da organização {}", organizacao.name());
        cartaRepository.deleteCartasByOrganizacao(organizacao);
    }
}
