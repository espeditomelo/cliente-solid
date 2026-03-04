package com.projeto.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public class ClienteRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF deve ser válido")
    private String cpf;

    private String telefone;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    public @NotBlank(message = "Nome é obrigatório") @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres") String getNome() {
        return nome;
    }

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(String nome, String email, String cpf, String telefone, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public void setNome(@NotBlank(message = "Nome é obrigatório") @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "Email é obrigatório") @Email(message = "Email deve ser válido") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email é obrigatório") @Email(message = "Email deve ser válido") String email) {
        this.email = email;
    }

    public @NotBlank(message = "CPF é obrigatório") @CPF(message = "CPF deve ser válido") String getCpf() {
        return cpf;
    }

    public void setCpf(@NotBlank(message = "CPF é obrigatório") @CPF(message = "CPF deve ser válido") String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public @Past(message = "Data de nascimento deve ser no passado") LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(@Past(message = "Data de nascimento deve ser no passado") LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
