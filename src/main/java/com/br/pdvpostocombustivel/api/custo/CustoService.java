package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import com.br.pdvpostocombustivel.domain.entity.Custo;
import com.br.pdvpostocombustivel.domain.repository.CustoRepository;
import com.br.pdvpostocombustivel.exception.EntidadeNaoEncontradaException; // <-- Import
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustoService {

    private final CustoRepository repository;

    public CustoService(CustoRepository repository) {
        this.repository = repository;
    }

    public CustoResponse create(CustoRequest req) {
        Custo custo = new Custo(req.imposto(), req.custoVariavel(), req.custoFixo(), req.margemLucro(), req.dataProcessameto());
        repository.save(custo);
        return toResponse(custo);
    }

    @Transactional(readOnly = true)
    public CustoResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Custo com código %d não encontrado", id))); // <-- Alterado
    }

    @Transactional(readOnly = true)
    public List<CustoResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CustoResponse update(Long id, CustoRequest req) {
        Custo custo = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Custo com código %d não encontrado", id))); // <-- Alterado
        custo.setImposto(req.imposto());
        custo.setCustoVariavel(req.custoVariavel());
        custo.setCustoFixo(req.custoFixo());
        custo.setMargemLucro(req.margemLucro());
        custo.setDataProcessamento(req.dataProcessameto());
        repository.save(custo);
        return toResponse(custo);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Custo com código %d não encontrado", id)); // <-- Alterado
        }
        repository.deleteById(id);
    }

    private CustoResponse toResponse(Custo custo) {
        return new CustoResponse(
                custo.getId(), custo.getImposto(), custo.getCustoVariavel(),
                custo.getCustoFixo(), custo.getMargemLucro(), custo.getDataProcessamento()
        );
    }
}