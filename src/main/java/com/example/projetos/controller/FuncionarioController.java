package com.example.projetos.controller;

import com.example.projetos.model.Funcionario;
import com.example.projetos.service.FuncionarioService;
import com.example.projetos.service.SetorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final SetorService setorService;

    public FuncionarioController(FuncionarioService funcionarioService, SetorService setorService) {
        this.funcionarioService = funcionarioService;
        this.setorService = setorService;
    }

    @GetMapping
    public String listarFuncionarios(Model model) {
        model.addAttribute("funcionarios", funcionarioService.listarTodos());
        return "funcionarios";
    }

    @GetMapping("/novo")
    public String formularioNovoFuncionario(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("setores", setorService.listarTodos());
        return "novo-funcionario";
    }

    @PostMapping
    public String adicionar(@ModelAttribute Funcionario funcionario) {
        funcionarioService.salvar(funcionario);
        return "redirect:/funcionarios";
    }

    @GetMapping("/{id}")
    public String buscarFuncionarioPorId(@PathVariable Integer id, Model model) {
        model.addAttribute("funcionario", funcionarioService.buscarPorId(id));
        model.addAttribute("projetos", funcionarioService.listarProjetosDoFuncionario(id));
        return "detalhes-funcionario";
    }
}
