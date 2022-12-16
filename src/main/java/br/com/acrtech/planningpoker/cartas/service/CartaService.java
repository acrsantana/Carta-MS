package br.com.acrtech.planningpoker.cartas.service;

import br.com.acrtech.planningpoker.cartas.dto.CartaDto;
import br.com.acrtech.planningpoker.cartas.exception.*;
import br.com.acrtech.planningpoker.cartas.http.OrganizacaoClient;
import br.com.acrtech.planningpoker.cartas.model.Carta;
import br.com.acrtech.planningpoker.cartas.model.Organizacao;
import br.com.acrtech.planningpoker.cartas.repository.CartaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service @Slf4j
public class CartaService {

    private final CartaRepository cartaRepository;
    private final OrganizacaoClient organizacaoClient;

    public CartaService(CartaRepository cartaRepository, OrganizacaoClient organizacaoClient) {
        this.cartaRepository = cartaRepository;
        this.organizacaoClient = organizacaoClient;
    }

    public List<CartaDto> findAll(){
        log.info("Buscando todas as cartas");
        List<CartaDto> cartasDto = cartaRepository.findAll().stream().map(CartaDto::new).toList();
        if (cartasDto.isEmpty()){
            log.error("Nenhuma carta encontrada");
            throw new CartaNaoEncontradaException("Nenhuma carta encontrada");
        }
        try {
            log.debug("Buscando todas as organizações no microserviço");
            List<Organizacao> organizacoes = organizacaoClient.findAll();
            if (organizacoes.isEmpty()){
                log.error("Nenhuma organização encontrada");
                throw new OrganizacaoNaoEncontradaException("Nenhuma organizacao encontrada");
            }
            cartasDto.forEach(carta -> organizacoes.forEach(organizacao -> {
                if (carta.getIdOrganizacao().equals(organizacao.getId())){
                    log.debug("Associando a carta {} a organização {}", carta.getValor(), organizacao.getNome());
                    carta.setOrganizacao(organizacao.getNome());
                }
            }));
            return cartasDto;
        } catch (Exception e) {
            log.error("Não foi possível buscar a lista de cartas");
            throw new ErroAoRecuperarCartasException("Não foi possível buscar a lista de cartas");
        }

    }

    public CartaDto findById(Long id) {
        log.info("Buscando a carta com id {}", id);
        Optional<Carta> optionalCarta = cartaRepository.findById(id);
        if (optionalCarta.isEmpty()){
            log.error("Carta com o id {} não encontrada", id);
            throw new CartaNaoEncontradaException("Carta com o id " + id + " não encontrada");
        }
        try {
            var carta = new CartaDto(optionalCarta.get());
            var organizacao = organizacaoClient.findById(carta.getIdOrganizacao());
            carta.setOrganizacao(organizacao.getNome());
            return carta;
        } catch (Exception e) {
            throw new OrganizacaoNaoEncontradaException(e.getMessage());
        }
    }

    public List<CartaDto> findAllByOrganizacao(Integer idOrganizacao){
        log.info("Buscando todas as cartas da organização {}", idOrganizacao);
        try {
            List<CartaDto> cartas = cartaRepository.findAllByIdOrganizacao(idOrganizacao).stream().map(CartaDto::new).toList();
            var organizacao = organizacaoClient.findById(idOrganizacao);
            cartas.forEach(carta -> carta.setOrganizacao(organizacao.getNome()));
            return cartas;
        } catch (Exception e) {
            log.error("Erro ao recuperar as cartas da organização {}", idOrganizacao);
            throw new ErroAoRecuperarCartasException("Erro ao recuperar as cartas, tente novamente mais tarde.");
        }

    }

    public CartaDto save(CartaDto carta) {
        log.info("Salvando a carta {}", carta.getValor());
        try {
            var organizacao = organizacaoClient.findById(carta.getIdOrganizacao());
            log.debug("Organização {} localizada com sucesso", organizacao.getNome());
            var cartaSalva = new CartaDto(cartaRepository.save(new Carta(carta)));
            cartaSalva.setOrganizacao(organizacao.getNome());
            return cartaSalva;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new  OrganizacaoNaoEncontradaException("A organização informada não foi encontrada");
        }


    }

    @Transactional
    public List<CartaDto> saveAll(List<CartaDto> cartas) {
        log.info("Salvando lista de cartas com {} cartas", cartas.size());
        try {
            List<Organizacao> organizacoes = organizacaoClient.findAll();
            if (organizacoes.isEmpty()){
                log.error("Nenhuma organização encontrada");
                throw new OrganizacaoNaoEncontradaException("Nenhuma organização encontrada");
            }

            if (cartas.isEmpty()) {
                log.error("Nenhuma carta informada");
                throw new CartaNaoInformadaException("Nenhuma carta informada");
            }

            cartas.forEach(carta -> {
                organizacoes.forEach(organizacao -> {
                    if (carta.getIdOrganizacao().equals(organizacao.getId())){
                        carta.setOrganizacao(organizacao.getNome());
                    }
                });
                if (Objects.isNull(carta.getOrganizacao())){
                    log.error("Organização {} não encontrada.", carta.getIdOrganizacao());
                    throw new OrganizacaoNaoEncontradaException("Organização " + carta.getIdOrganizacao() + " não encontrada");
                }
            });

            List<Carta> list = cartaRepository.saveAll(cartas.stream().map(Carta::new).toList());
            List<CartaDto> dtos = list.stream().map(CartaDto::new).toList();
            dtos.forEach(carta -> {
                organizacoes.forEach(organizacao -> {
                    if (carta.getIdOrganizacao().equals(organizacao.getId())) {
                        carta.setOrganizacao(organizacao.getNome());
                    }
                });
            });
            return dtos;
        }catch (OrganizacaoNaoEncontradaException e) {
            throw new OrganizacaoNaoEncontradaException(e.getMessage());
        } catch (CartaNaoInformadaException e) {
            throw new CartaNaoInformadaException(e.getMessage());
        } catch (Exception e) {
            log.error("Erro ao tentar salvar lista de cartas.");
            throw new ErroAoSalvarCartasException("Erro ao tentar salvar lista de cartas. Tente novamente mais tarde");
        }

    }

    public void delete(Long id) {
        log.info("Deletando a carta com id {}", id);
        cartaRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByOrganizacao(Integer idOrganizacao) {
        log.info("Deletando todas as cartas da organização {}", idOrganizacao);
        cartaRepository.deleteCartasByIdOrganizacao(idOrganizacao);
    }
}
