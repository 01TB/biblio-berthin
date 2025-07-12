package com.springjpa.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springjpa.bibliotheque.entity.Adherent;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/adherent")
public class AdherentController {

    @GetMapping
    public String home(HttpSession session, Model model) {
        Adherent adherent = (Adherent)session.getAttribute("adherent");
        if(adherent==null){
            model.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }

        return "adherent/home";
    }

}