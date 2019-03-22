package br.org.stefan.ziel.cdf.service;

import br.org.stefan.ziel.cdf.domain.Embarque;
import br.org.stefan.ziel.cdf.domain.Resultado;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Resultado.
 */
public interface ResultadoService {

	/**
     * Criar um novo resultado a partir do embarque selecionando a melhor negociçao de frete.
     *
     * @param embarque embarque em questão 
     * @return o resultado de calculo persistido
     */
    Resultado calcularFrete(Embarque embarque);
	
	/**
     * Criar uma Lista de novos resultados a a serem embarcados numa data.
     *
     * @param dataDeColeta a data da coleta (normalmente a data corrente) 
     * @return uma lista de resultados de calculo persistidos
     */
    List<Resultado> calcularFretesDoDia(Instant dataDeColeta);

    /**
     * Save a resultado.
     *
     * @param resultado the entity to save
     * @return the persisted entity
     */
    Resultado save(Resultado resultado);

    /**
     * Get all the resultados.
     *
     * @return the list of entities
     */
    List<Resultado> findAll();


    /**
     * Get the "id" resultado.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Resultado> findOne(Long id);

    /**
     * Delete the "id" resultado.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
