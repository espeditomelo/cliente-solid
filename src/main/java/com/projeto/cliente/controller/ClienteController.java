package com.projeto.cliente.controller;

/*
Endpoints:
  POST /api/clientes -> criar cliente
  GET /api/clientes -> listar todos clientes
  GET /api/clientes/{id} -> Buscar um cliente por id
  PUT /api/clientes/{id} -> Atualizar um cliente por id
  DELETE /api/clientes/{id} -> Remover um cliente por id
 */

import com.projeto.cliente.dto.ClienteRequestDTO;
import com.projeto.cliente.dto.ClienteResponseDTO;
import com.projeto.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // POST /api/clientes retorna 201 created ou 404 se não for criado
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO response = clienteService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/clientes retorna 200 se OK
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    // GET /api/clientes/{id} retorna 200 se OK ou 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    // PUT /api/clientes/{id} retorna 200 se OK ou 400 se Not Found
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.atualizar(id, dto));
    }

    // DELETE /api/clientes/{id} se ok retorna o status HTTP 204 = No Content do contrario retorna HTTP 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
