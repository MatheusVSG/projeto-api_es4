package com.fatec.projeto.es4.api_es4.domain.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.projeto.es4.api_es4.entities.Cliente;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(Cliente cliente) {
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        List<Object[]> resultados = clienteRepository.listarClientes();

        for (Object[] linha : resultados) {
            Cliente cliente = new Cliente();

            Long id = ((Number) linha[0]).longValue();
            String cnpj = (String) linha[1];
            String nomeFantasia = (String) linha[2];
            String municipio = (String) linha[3];

            cliente.setId(id);
            cliente.setCnpj(cnpj);
            cliente.setNomeFantasia(nomeFantasia);
            cliente.setMunicipio(municipio);

            clientes.add(cliente);
        }

        return clientes;
    }

    public List<Cliente> buscarClientesPorTermo(String termo) {
        List<Cliente> clientes = new ArrayList<>();
        List<Object[]> resultados = clienteRepository.buscarClientesPorTermo(termo);

        for (Object[] linha : resultados) {
            Cliente cliente = new Cliente();

            Long id = ((Number) linha[0]).longValue();
            String cnpj = (String) linha[1];
            String nomeFantasia = (String) linha[2];
            String municipio = (String) linha[3];

            cliente.setId(id);
            cliente.setCnpj(cnpj);
            cliente.setNomeFantasia(nomeFantasia);
            cliente.setMunicipio(municipio);

            clientes.add(cliente);
        }

        return clientes;
    }

    public Optional<Cliente> buscarClienteDetalhado(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente;
    }

    public boolean atualizarCliente(Long id, Cliente cliente) {
        Optional<Cliente> clienteExiste = this.buscarClienteDetalhado(id);
        if (clienteExiste.isPresent()) {
            Cliente c = clienteExiste.get();
            c.setDataAbertura(cliente.getDataAbertura());
            c.setNomeFantasia(cliente.getNomeFantasia());
            c.setLoja(cliente.getLoja());
            c.setTipo(cliente.getTipo());
            c.setDdd(cliente.getDdd());
            c.setTelefone(cliente.getTelefone());
            c.setEmail(cliente.getEmail());
            c.setHomePage(cliente.getHomePage());
            c.setCep(cliente.getCep());
            c.setLogradouro(cliente.getLogradouro());
            c.setBairro(cliente.getBairro());
            c.setCodMunicipio(cliente.getCodMunicipio());
            c.setMunicipio(cliente.getMunicipio());
            c.setEstado(cliente.getEstado());
            clienteRepository.save(c);
            return true;
        }
        return false;
    }

    public boolean excluirCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean verificarCnpjExistente(String cnpj) {
        return clienteRepository.existsByCnpj(cnpj);
    }

    public boolean verificarTelefoneExistente(int ddd, String telefone) {
        return clienteRepository.existsByDddAndTelefone(ddd, telefone);
    }

    public boolean verificarTelefoneExistenteComIdDiferente(int ddd, String telefone, Long id) {
        return clienteRepository.existsByDddAndTelefoneAndIdNot(ddd, telefone, id);
    }

    public boolean verificarEmailExistente(String email) {
        return clienteRepository.existsByEmail(email);
    }

    public boolean verificarEmailExistenteComIdDiferente(String email, Long id) {
        return clienteRepository.existsByEmailAndIdNot(email, id);
    }
}
