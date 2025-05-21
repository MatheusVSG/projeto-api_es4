package com.fatec.projeto.es4.api_es4.domain.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatec.projeto.es4.api_es4.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCnpj(String cnpj);

    boolean existsByDddAndTelefone(int ddd, String telefone);

    boolean existsByDddAndTelefoneAndIdNot(int ddd, String telefone, Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    @Query(value = "SELECT c.id, c.cnpj, c.nome_fantasia, c.municipio FROM clientes c", nativeQuery = true)
    List<Object[]> listarClientes();

    @Query(value = "SELECT c.id, c.cnpj, c.nome_fantasia, c.municipio " +
            "FROM clientes c " +
            "WHERE LOWER(c.nome_fantasia) LIKE LOWER(CONCAT('%', :param, '%')) " +
            "   OR LOWER(c.cnpj) LIKE LOWER(CONCAT('%', :param, '%')) " +
            "   OR LOWER(c.municipio) LIKE LOWER(CONCAT('%', :param, '%'))", nativeQuery = true)
    List<Object[]> buscarClientesPorTermo(@Param("param") String param);
}
