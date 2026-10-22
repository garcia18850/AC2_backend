package com.example.projetos.controller;

import com.example.projetos.model.Setor;
import com.example.projetos.service.SetorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @GetMapping
    public String listarSetores(Model model) {
        model.addAttribute("setores", setorService.listarTodosComFuncionarios());
        return "setores";
    }

    @GetMapping("/novo")
    public String formularioNovoSetor(Model model) {
        model.addAttribute("setor", new Setor());
        return "novo-setor";
    }

    @PostMapping
    public String adicionar(@ModelAttribute Setor setor) {
        setorService.salvar(setor);
        return "redirect:/setores";
    }

    @GetMapping("/{id}")
    public String buscarSetorPorId(@PathVariable Integer id, Model model) {
        model.addAttribute("setor", setorService.buscarPorId(id));
        return "detalhes-setor";
    }
}
