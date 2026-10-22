package com.example.projetos.service;

import com.example.projetos.model.Setor;
import com.example.projetos.repository.SetorRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public List<Setor> listarTodos() {
        return setorRepository.findAll();
    }

    public List<Setor> listarTodosComFuncionarios() {
        return setorRepository.findAllComFuncionarios();
    }

    public Setor salvar(Setor setor) {
        if (setor.getNome() == null || setor.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do setor é obrigatório.");
        }
        return setorRepository.save(setor);
    }

    public Setor buscarPorId(Integer id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado: " + id));
    }
}
