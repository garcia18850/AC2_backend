package com.example.projetos.service;

import com.example.projetos.model.Funcionario;
import com.example.projetos.model.Projeto;
import com.example.projetos.model.Setor;
import com.example.projetos.repository.FuncionarioRepository;
import com.example.projetos.repository.SetorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final SetorRepository setorRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, SetorRepository setorRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.setorRepository = setorRepository;
    }

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario salvar(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do funcionário é obrigatório.");
        }
        if (funcionario.getSetor() == null || funcionario.getSetor().getId() == null) {
            throw new IllegalArgumentException("Setor do funcionário é obrigatório.");
        }

        Setor setor = setorRepository.findById(funcionario.getSetor().getId())
                .orElseThrow(() -> new RuntimeException("Setor não encontrado: " + funcionario.getSetor().getId()));
        funcionario.setSetor(setor);

        return funcionarioRepository.save(funcionario);
    }

    public Funcionario buscarPorId(Integer id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado: " + id));
    }

    public List<Projeto> listarProjetosDoFuncionario(Integer idFuncionario) {
        return funcionarioRepository.findProjetosByFuncionarioId(idFuncionario);
    }
}
