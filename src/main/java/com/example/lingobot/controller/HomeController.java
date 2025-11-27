package com.example.lingobot.controller;

import com.example.lingobot.model.ExercicioLog;
import com.example.lingobot.service.TutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final TutorService tutorService;

    public HomeController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @GetMapping("/")
    public String index(Model model) {
        String desafio = tutorService.gerarDesafio();
        model.addAttribute("fraseIngles", desafio);
        return "index";
    }

    @PostMapping("/corrigir")
    public String corrigir(@RequestParam("fraseOriginal") String fraseOriginal,
                           @RequestParam("traducaoUsuario") String traducaoUsuario,
                           Model model) {

        ExercicioLog resultado = tutorService.corrigirExercicio(fraseOriginal, traducaoUsuario);
        model.addAttribute("resultado", resultado);
        return "resultado";
    }

    @GetMapping("/historico")
    public String historico(Model model) {
        return "index";
    }
}