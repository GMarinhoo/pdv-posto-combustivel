package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import com.br.pdvpostocombustivel.domain.entity.Custo;
import com.br.pdvpostocombustivel.domain.repository.CustoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustoService {

    private final CustoRepository repository;

    public CustoService(CustoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CustoResponse create(CustoRequest req) {
        Custo custo = new Custo(req.imposto(), req.custoVariavel(), req.custoFixo(), req.margemLucro(), req.dataProcessameto());
        repository.save(custo);
        return toResponse(custo);
    }

    public CustoResponse getById(Long id) {
        Custo custo = repository.findById(id).orElseThrow(() -> new RuntimeException("Custo não encontrado"));
        return toResponse(custo);
    }

    public List<CustoResponse> listAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustoResponse update(Long id, CustoRequest req) {
        Custo custo = repository.findById(id).orElseThrow(() -> new RuntimeException("Custo não encontrado"));
        custo.setImposto(req.imposto());
        custo.setCustoVariavel(req.custoVariavel());
        custo.setCustoFixo(req.custoFixo());
        custo.setMargemLucro(req.margemLucro());
        custo.setDataProcessamento(req.dataProcessameto());
        repository.save(custo);
        return toResponse(custo);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Custo não encontrado");
        }
        repository.deleteById(id);
    }

    private CustoResponse toResponse(Custo custo) {
        return new CustoResponse(
                custo.getId(),
                custo.getImposto(),
                custo.getCustoVariavel(),
                custo.getCustoFixo(),
                custo.getMargemLucro(),
                custo.getDataProcessamento()
        );
    }
}