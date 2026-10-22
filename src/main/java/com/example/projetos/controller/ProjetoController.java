package com.example.projetos.controller;

import com.example.projetos.model.Projeto;
import com.example.projetos.service.ProjetoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @GetMapping
    public String listarProjetos(Model model) {
        model.addAttribute("projetos", projetoService.listarTodos());
        return "projetos";
    }

    @GetMapping("/novo")
    public String formularioNovoProjeto(Model model) {
        model.addAttribute("projeto", new Projeto());
        return "novo-projeto";
    }

    @PostMapping
    public String adicionar(@ModelAttribute Projeto projeto) {
        projetoService.salvar(projeto);
        return "redirect:/projetos";
    }

    @GetMapping("/{id}")
    public String buscarProjetoPorId(@PathVariable Integer id, Model model) {
        model.addAttribute("projeto", projetoService.buscarPorIdComFuncionarios(id));
        return "detalhes-projeto";
    }

    @PostMapping("/{idProjeto}/vincular")
    public String vincularFuncionario(@PathVariable Integer idProjeto, @RequestParam Integer idFuncionario) {
        projetoService.vincularFuncionario(idProjeto, idFuncionario);
        return "redirect:/projetos/" + idProjeto;
    }
}
