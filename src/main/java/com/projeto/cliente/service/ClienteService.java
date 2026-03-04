package com.projeto.cliente.service;

import com.projeto.cliente.dto.ClienteRequestDTO;
import com.projeto.cliente.dto.ClienteResponseDTO;
import com.projeto.cliente.exception.BusinessException;
import com.projeto.cliente.exception.ResourceNotFoundException;
import com.projeto.cliente.model.Cliente;
import com.projeto.cliente.repository.ClienteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    // construtor manual
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já existe " + dto.getEmail());
        }

        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já existe " + dto.getCpf());
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());

        return ClienteResponseDTO.from(clienteRepository.save(cliente));

    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não localizado com id: " + id));
        return ClienteResponseDTO.from(cliente);
    }

    @Transactional
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com este id: " + id));

        if (!cliente.getEmail().equals(dto.getEmail()) && clienteRepository.existsByEmail(dto.getEmail()) ) {
            throw new BusinessException("Este email já foi cadasrado: " + dto.getEmail());
        }

        if (!cliente.getCpf().equals(dto.getCpf()) && clienteRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("Este CPF já foi cadastrado: " + dto.getCpf());
        }

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());

        return ClienteResponseDTO.from(clienteRepository.save(cliente));
    }

    @Transactional
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
        clienteRepository.deleteById(id);
    }


}
