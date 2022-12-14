package br.com.acrtech.planningpoker.cartas.controller;

import br.com.acrtech.planningpoker.cartas.dto.CartaDto;
import br.com.acrtech.planningpoker.cartas.exception.CartaNaoEncontradaException;
import br.com.acrtech.planningpoker.cartas.model.Organizacao;
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
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cartaService.findById(id));
        } catch (CartaNaoEncontradaException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/organizacao/{organizacao}")
    public ResponseEntity<List<CartaDto>> findAllByOrganizacao(@PathVariable Organizacao organizacao){
        return ResponseEntity.ok(cartaService.findAllByOrganizacao(organizacao));
    }

    @PostMapping
    public ResponseEntity<CartaDto> save(@RequestBody @Valid CartaDto cartaDto){
        return ResponseEntity.ok(cartaService.save(cartaDto));
    }

    @PostMapping("/todas")
    public ResponseEntity<List<CartaDto>> saveAll(@RequestBody @Valid List<CartaDto> cartas) {
        return ResponseEntity.ok(cartaService.saveAll(cartas));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        cartaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/organizacao/{organizacao}")
    public ResponseEntity<HttpStatus> deleteByOrganizacao(@PathVariable Organizacao organizacao) {
        cartaService.deleteAllByOrganizacao(organizacao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
