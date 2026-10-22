package com.example.projetos.service;

import com.example.projetos.model.Funcionario;
import com.example.projetos.model.Projeto;
import com.example.projetos.repository.FuncionarioRepository;
import com.example.projetos.repository.ProjetoRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public ProjetoService(ProjetoRepository projetoRepository, FuncionarioRepository funcionarioRepository) {
        this.projetoRepository = projetoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

    public Projeto salvar(Projeto projeto) {
        if (projeto.getDescricao() == null || projeto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição do projeto é obrigatória.");
        }
        if (projeto.getDataInicio() == null || projeto.getDataFim() == null) {
            throw new IllegalArgumentException("Data de início e fim são obrigatórias.");
        }
        if (projeto.getDataFim().isBefore(projeto.getDataInicio())) {
            throw new IllegalArgumentException("Data de fim não pode ser anterior à data de início.");
        }
        return projetoRepository.save(projeto);
    }

    public Projeto buscarPorId(Integer id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + id));
    }

    public Projeto buscarPorIdComFuncionarios(Integer id) {
        return projetoRepository.findByIdComFuncionarios(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + id));
    }

    public List<Projeto> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
        return projetoRepository.findByDataInicioBetween(inicio, fim);
    }

    @Transactional
    public void vincularFuncionario(Integer idProjeto, Integer idFuncionario) {
        Projeto projeto = buscarPorId(idProjeto);
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado: " + idFuncionario));

        if (!projeto.getFuncionarios().contains(funcionario)) {
            projeto.getFuncionarios().add(funcionario);
            funcionario.getProjetos().add(projeto);
        }

        projetoRepository.save(projeto);
    }
}
