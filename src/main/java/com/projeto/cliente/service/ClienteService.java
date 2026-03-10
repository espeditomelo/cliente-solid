package com.projeto.cliente.service;

import com.projeto.cliente.dto.ClienteRequestDTO;
import com.projeto.cliente.dto.ClienteResponseDTO;
import com.projeto.cliente.exception.BusinessException;
import com.projeto.cliente.exception.ResourceNotFoundException;
import com.projeto.cliente.model.Cliente;
import com.projeto.cliente.repository.ClienteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    // construtor manual
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {

        log.info(">>>>>>>>> Iniciando a criação de cadastro de cliente com o email: {}", dto.getEmail());

        if (clienteRepository.existsByEmail(dto.getEmail())) {
            log.warn(">>>>>> Tentativa de cadastrar cliente com email existente: {}", dto.getEmail());
            throw new BusinessException("Email já existe " + dto.getEmail());
        }

        if (clienteRepository.existsByCpf(dto.getCpf())) {
            log.warn(">>>>>> Tentativa de cadastrar cliente com CPF existente: {}", dto.getCpf());
            throw new BusinessException("CPF já existe " + dto.getCpf());
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());

        Cliente clienteSalvo = clienteRepository.save(cliente);
        log.info("Cliente inserido com sucesso. id: {}, email: {}", clienteSalvo.getId(), clienteSalvo.getEmail());

        return ClienteResponseDTO.from(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        log.info(">>>>>>>>>> Listando todos os clientes cadastrados");
        List<ClienteResponseDTO> clientes = clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::from)
                .collect(Collectors.toList());

        return clientes;
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        log.info("Consultando cliente por id: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente com id {} não encontrado", id);
                    return new ResourceNotFoundException("Cliente não localizado com id: " + id);
                });
        log.info("Cliente encontrado: {}", cliente.getEmail());
        return ClienteResponseDTO.from(cliente);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        log.info("Localizando cliente com id {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente com id {} não encontrado para atualização", id);
                    return new ResourceNotFoundException("Cliente não encontrado com id: " + id);
                });

        if (!cliente.getEmail().equals(dto.getEmail()) && clienteRepository.existsByEmail(dto.getEmail()) ) {
            log.warn("Já existe um cliente cadastrado com o email: {}", dto.getEmail());
            throw new BusinessException("Este email já foi cadasrado: " + dto.getEmail());
        }

        if (!cliente.getCpf().equals(dto.getCpf()) && clienteRepository.existsByCpf(dto.getCpf())) {
            log.warn("Já existe um cliente cadastrado com o CPF: {}", dto.getCpf());
            throw new BusinessException("Este CPF já foi cadastrado: " + dto.getCpf());
        }

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return ClienteResponseDTO.from(clienteAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        log.info("Iniciando a localização do cliente com id {} para exclusão", id);
        if (!clienteRepository.existsById(id)) {
            log.warn("Cliente com id {} não foi encontrado", id);
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
        clienteRepository.deleteById(id);
        log.info("Cliente com id {} excluído com sucesso", id);
    }


}
