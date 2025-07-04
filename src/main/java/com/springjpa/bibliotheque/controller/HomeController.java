package com.springjpa.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home"; // Redirection vers la page d'accueil
    }

    @PostMapping("/login/admin")
    public String connexionAdmin(@RequestParam("admin-matricule") int matriculeAdmin,
                                 @RequestParam("admin-password") String adminPassword) {

        return "redirect:/pret";
    }

    // @PostMapping("/login/adherent")
    
}
