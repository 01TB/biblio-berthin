package com.springjpa.bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springjpa.bibliotheque.entity.Admin;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String home(HttpSession session, Model model) {
        Admin admin = (Admin)session.getAttribute("admin");
        if(admin==null){
            model.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }

        model.addAttribute("admin",admin);
        return "admin/home";
    }

    @GetMapping("/livre")
    public String livre(HttpSession session, Model model) {
        Admin admin = (Admin)session.getAttribute("admin");
        if(admin==null){
            model.addAttribute("message", "Tentative d'attaque");
            return "redirect:/";
        }

        model.addAttribute("admin",admin);
        return "admin/livre";
    }

}