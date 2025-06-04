package com.fatec.projeto.es4.api_es4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.projeto.es4.api_es4.domain.MailService;
import com.fatec.projeto.es4.api_es4.domain.cliente.ClienteService;
import com.fatec.projeto.es4.api_es4.entities.Cliente;
import com.fatec.projeto.es4.api_es4.entities.MailStructure;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MailService mailService;

    //cadastro de cliente

    @PostMapping("/cadastrar-cliente")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) throws MessagingException {
        Cliente c = clienteService.cadastrarCliente(cliente);
        if (c == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // ou outra resposta adequada
        }
        MailStructure mailStructure = new MailStructure(c);
        mailStructure.setSubject("Novo Cliente Cadastrado");
        mailService.senMail("carloseduardomimi@gmail.com", mailStructure);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @GetMapping("/listar-clientes")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/buscar-clientes-por-termo/{termo}")
    public ResponseEntity<List<Cliente>> buscarClientesPorTermo(@PathVariable String termo) {
        List<Cliente> clientes = clienteService.buscarClientesPorTermo(termo);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/buscar-cliente-detalhado/{id}")
    public ResponseEntity<Optional<Cliente>> bucarClienteDetalhado(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarClienteDetalhado(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PutMapping("/atualizar-cliente/{id}")
    public ResponseEntity editarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        boolean clienteAtualizado = clienteService.atualizarCliente(id, cliente);
        if (clienteAtualizado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/excluir-cliente/{id}")
    public ResponseEntity excluirCliente(@PathVariable Long id) {
        boolean excluido = clienteService.excluirCliente(id);
        if (excluido) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** Verificações */

    @GetMapping("/verificar-cnpj/{cnpj}")
    public ResponseEntity<Map<String, String>> verificarCnpjExistente(@PathVariable String cnpj) {
        boolean cnpjExiste = clienteService.verificarCnpjExistente(cnpj);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", cnpjExiste ? "CNPJ já cadastrado." : "CNPJ disponível.");
        resposta.put("status", cnpjExiste ? "CONFLITO" : "OK");

        return ResponseEntity
                .status(cnpjExiste ? HttpStatus.CONFLICT : HttpStatus.OK)
                .body(resposta);
    }

    // Verificação do Telefone

    @GetMapping("/verificar-telefone")
    public ResponseEntity<Map<String, String>> verificarTelefoneExistente(
            @RequestParam int ddd,
            @RequestParam String telefone) {

        boolean telefoneExiste = clienteService.verificarTelefoneExistente(ddd, telefone);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", telefoneExiste ? "Telefone já cadastrado." : "Telefone disponível.");
        resposta.put("status", telefoneExiste ? "CONFLITO" : "OK");

        return ResponseEntity
                .status(telefoneExiste ? HttpStatus.CONFLICT : HttpStatus.OK)
                .body(resposta);
    }

    // Verificação do Telefone

    @GetMapping("/verificar-telefone/id-diferente")
    public ResponseEntity<Map<String, String>> verificarTelefoneExistenteComIdDiferente(
            @RequestParam int ddd,
            @RequestParam String telefone,
            @RequestParam Long id) {

        boolean telefoneExiste = clienteService.verificarTelefoneExistenteComIdDiferente(ddd, telefone, id);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", telefoneExiste ? "Telefone já cadastrado." : "Telefone disponível.");
        resposta.put("status", telefoneExiste ? "CONFLITO" : "OK");

        return ResponseEntity
                .status(telefoneExiste ? HttpStatus.CONFLICT : HttpStatus.OK)
                .body(resposta);
    }

    // Verificação do Email

    @GetMapping("/verificar-email/{email}")
    public ResponseEntity<Map<String, String>> verificarEmailExistente(@PathVariable String email) {
        boolean emailExiste = clienteService.verificarEmailExistente(email);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", emailExiste ? "E-mail já cadastrado." : "E-mail disponível.");
        resposta.put("status", emailExiste ? "CONFLITO" : "OK");

        return ResponseEntity
                .status(emailExiste ? HttpStatus.CONFLICT : HttpStatus.OK)
                .body(resposta);
    }

    // Verificação do Email

    @GetMapping("/verificar-email")
    public ResponseEntity<Map<String, String>> verificarEmailExistenteComIdDiferente(
            @RequestParam String email,
            @RequestParam Long id) {

        boolean emailExiste = clienteService.verificarEmailExistenteComIdDiferente(email, id);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", emailExiste ? "E-mail já cadastrado." : "E-mail disponível.");
        resposta.put("status", emailExiste ? "CONFLITO" : "OK");

        return ResponseEntity
                .status(emailExiste ? HttpStatus.CONFLICT : HttpStatus.OK)
                .body(resposta);
    }
}
