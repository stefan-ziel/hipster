package br.org.stefan.ziel.cdf.service.impl;

import br.org.stefan.ziel.cdf.service.ResultadoService;
import br.org.stefan.ziel.cdf.domain.Embarque;
import br.org.stefan.ziel.cdf.domain.NegociacaoDeFrete;
import br.org.stefan.ziel.cdf.domain.Resultado;
import br.org.stefan.ziel.cdf.repository.EmbarqueRepository;
import br.org.stefan.ziel.cdf.repository.NegociacaoDeFreteRepository;
import br.org.stefan.ziel.cdf.repository.ResultadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Resultado.
 */
@Service
@Transactional
public class ResultadoServiceImpl implements ResultadoService {
	private static final Duration ONE_DAY = Duration.ofDays(1);
	
    private final Logger log = LoggerFactory.getLogger(ResultadoServiceImpl.class);

    private final ResultadoRepository resultadoRepository;
    
    private final NegociacaoDeFreteRepository negociacaodeFreteRepository;
    
    private final EmbarqueRepository embarqueRepository;

    public ResultadoServiceImpl(ResultadoRepository resultadoRepository, NegociacaoDeFreteRepository negociacaodeFreteRepository, EmbarqueRepository embarqueRepository) {
        this.resultadoRepository = resultadoRepository;
        this.negociacaodeFreteRepository = negociacaodeFreteRepository;
        this.embarqueRepository = embarqueRepository;
    }

    /**
     * Criar um novo resultado a partir do embarque selecionando a melhor negociçao de frete.
     *
     * @param embarqueId embarque em questão 
     * @return o resultado de calculo persistido
     */
	@Override
	public Optional<Resultado> calcularFrete(Long embarqueId) {
		Resultado resultado;
		Optional<Embarque> embarque = embarqueRepository.findById(embarqueId);
		if(embarque.isPresent()) {
			resultado = calcularFrete(embarque.get());
		} else {
			log.warn("No embarque found for id: {}", embarqueId);
			resultado = null;
		}
		return Optional.ofNullable(resultado);
	}
	
    /**
     * Criar um novo resultado a partir do embarque selecionando a melhor negociçao de frete.
     *
     * @param embarque embarque em questão 
     * @return o resultado de calculo persistido
     */
	Resultado calcularFrete(Embarque embarque) {
		Optional<Resultado> resultadoExistente = resultadoRepository.findByEmbarque(embarque);
		if(resultadoExistente.isPresent()) {
			// já calculado que bom
			return resultadoExistente.get();
		}
		
		NegociacaoDeFrete melhorNegociacaoDeFrete = null;
		BigDecimal menorValorCalculado = null;
		List<NegociacaoDeFrete> negociacoes = negociacaodeFreteRepository.findByEmbarcadoraAndCategoriaDeVeiculo(embarque.getEmbarcadora(), embarque.getCategoriaDeVeiculo());
		for(NegociacaoDeFrete negociacao : negociacoes) {
			// Dentro da faixa e peso
			if(negociacao.getPesoDe().compareTo(embarque.getPeso()) <= 0 && negociacao.getPesoAte().compareTo(embarque.getPeso()) >= 0 ) {
				BigDecimal valorCalculado = negociacao.getPrecoPorQuilometro().multiply(embarque.getQilometragem());
				if(menorValorCalculado == null || valorCalculado.compareTo(menorValorCalculado) < 0) {
					// em casos dem valorCalculado igual vence a primeira encontrada.
					melhorNegociacaoDeFrete = negociacao;
					menorValorCalculado = valorCalculado;
				}
			}
		}
		
		// nada encotrado
		if(melhorNegociacaoDeFrete == null) {
			log.warn("No match found for : {}", embarque);
			return null;
		}

		// Variaveis locais para depuração ...
		Instant previsaoDeEntrega = embarque.getDataDeColeta().plus(Duration.ofDays(melhorNegociacaoDeFrete.getPrazoDeEntrega()));
		Resultado resultado = new Resultado().embarque(embarque).melhorNegociacaoDeFrete(melhorNegociacaoDeFrete).previsaoDeEntrega(previsaoDeEntrega).valorCalculado(menorValorCalculado);
		return resultadoRepository.save(resultado);
	}

	/**
     * Criar uma Lista de novos resultados a a serem embarcados numa data.
     *
     * @param dataDeColeta a data da coleta (normalmente a data corrente) 
     * @return uma lista de resultados de calculo persistidos
     */
	@Override
	public List<Resultado> calcularFretesDoDia(Instant dataDeColeta) {
		// do instante um dia. Poderia ser configuravel
		Instant dataDeColetaAte = dataDeColeta.plus(ONE_DAY);
		
		/* 
		 * ordenar embarques por embarcadora e confiar que o ehcache resolve ...
		 * TODO monitorar performance !!! 
		 */
		List<Embarque> embarques = embarqueRepository.findByDataDeColetaBetweenOrderByEmbarcadora(dataDeColeta,dataDeColetaAte);
		List<Resultado> resultados = new ArrayList<Resultado>(embarques.size());
		
		for(Embarque embarque : embarques) {
			Resultado resultado = calcularFrete(embarque);
			if(resultado != null) {
				resultados.add(resultado);
			}
		}
		return resultados;
	}

	
    /**
     * Save a resultado.
     *
     * @param resultado the entity to save
     * @return the persisted entity
     */
    @Override
    public Resultado save(Resultado resultado) {
        log.debug("Request to save Resultado : {}", resultado);
        return resultadoRepository.save(resultado);
    }

    /**
     * Get all the resultados.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Resultado> findAll() {
        log.debug("Request to get all Resultados");
        return resultadoRepository.findAll();
    }


    /**
     * Get one resultado by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Resultado> findOne(Long id) {
        log.debug("Request to get Resultado : {}", id);
        return resultadoRepository.findById(id);
    }

    /**
     * Delete the resultado by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resultado : {}", id);
        resultadoRepository.deleteById(id);
    }

}
