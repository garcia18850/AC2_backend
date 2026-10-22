package com.example.projetos.config;

import com.example.projetos.model.Funcionario;
import com.example.projetos.model.Projeto;
import com.example.projetos.model.Setor;
import com.example.projetos.repository.FuncionarioRepository;
import com.example.projetos.repository.ProjetoRepository;
import com.example.projetos.repository.SetorRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SetorRepository setorRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ProjetoRepository projetoRepository;

    public DataInitializer(SetorRepository setorRepository,
            FuncionarioRepository funcionarioRepository,
            ProjetoRepository projetoRepository) {
        this.setorRepository = setorRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.projetoRepository = projetoRepository;
    }

    @Override
    public void run(String... args) {
        if (setorRepository.count() > 0) {
            return;
        }

        Setor ti = new Setor();
        ti.setNome("TI");
        setorRepository.save(ti);

        Setor financeiro = new Setor();
        financeiro.setNome("Financeiro");
        setorRepository.save(financeiro);

        Funcionario joao = new Funcionario();
        joao.setNome("João Silva");
        joao.setSetor(ti);
        funcionarioRepository.save(joao);

        Funcionario maria = new Funcionario();
        maria.setNome("Maria Oliveira");
        maria.setSetor(financeiro);
        funcionarioRepository.save(maria);

        Projeto projeto = new Projeto();
        projeto.setDescricao("Sistema de Gestão");
        projeto.setDataInicio(LocalDate.of(2026, 1, 10));
        projeto.setDataFim(LocalDate.of(2026, 3, 20));
        projeto.setFuncionarios(new ArrayList<>(List.of(joao, maria)));
        projetoRepository.save(projeto);
    }
}
