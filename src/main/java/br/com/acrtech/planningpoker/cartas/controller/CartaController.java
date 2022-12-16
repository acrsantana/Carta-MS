package br.com.acrtech.planningpoker.cartas.controller;

import br.com.acrtech.planningpoker.cartas.dto.CartaDto;
import br.com.acrtech.planningpoker.cartas.exception.CartaNaoEncontradaException;
import br.com.acrtech.planningpoker.cartas.exception.CartaNaoInformadaException;
import br.com.acrtech.planningpoker.cartas.exception.ErroAoRecuperarCartasException;
import br.com.acrtech.planningpoker.cartas.exception.ErroAoSalvarCartasException;
import br.com.acrtech.planningpoker.cartas.exception.OrganizacaoNaoEncontradaException;
import br.com.acrtech.planningpoker.cartas.service.CartaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CartaController {

    private final CartaService cartaService;

    public CartaController(CartaService cartaService) {
        this.cartaService = cartaService;
    }

    @GetMapping
    public ResponseEntity<List<CartaDto>> findAll(){
        return ResponseEntity.ok(cartaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cartaService.findById(id));
        } catch (CartaNaoEncontradaException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/organizacao/{idOrganizacao}")
    public ResponseEntity<List<CartaDto>> findAllByOrganizacao(@PathVariable Integer idOrganizacao){
        try {
            return ResponseEntity.ok(cartaService.findAllByOrganizacao(idOrganizacao));
        } catch (ErroAoRecuperarCartasException e) {
            throw new ErroAoRecuperarCartasException(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<CartaDto> save(@RequestBody @Valid CartaDto cartaDto){
        return ResponseEntity.ok(cartaService.save(cartaDto));
    }

    @PostMapping("/todas")
    public ResponseEntity<List<CartaDto>> saveAll(@RequestBody @Valid List<CartaDto> cartas) {
        try {
            return ResponseEntity.ok(cartaService.saveAll(cartas));
        } catch (OrganizacaoNaoEncontradaException | CartaNaoInformadaException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (ErroAoSalvarCartasException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cartaService.delete(id);
    }

    @DeleteMapping("/organizacao/{organizacao}")
    public void deleteByOrganizacao(@PathVariable Integer organizacao) {
        cartaService.deleteAllByOrganizacao(organizacao);
    }
}
