package com.springjpa.bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springjpa.bibliotheque.entity.Adherent;
import com.springjpa.bibliotheque.entity.Admin;
import com.springjpa.bibliotheque.service.AdherentService;
import com.springjpa.bibliotheque.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdherentService adherentService;

    @GetMapping("")
    public String index() {
        // Page d'accueil
        return "login"; // Redirection vers la page d'accueil
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        // Déconnexion
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("adherent")
    public String adherent() {
        // Page d'acceuil d'adhérent
        return "admin/home"; // Redirection vers la page d'accueil
    }

    @PostMapping("login/admin")
    public String connexionAdmin(@RequestParam("admin-matricule") int matriculeAdmin,
                                 @RequestParam("admin-password") String adminPassword,
                                 HttpSession session, Model model) {

        if (adminService.isAdmin(matriculeAdmin, adminPassword)) {
            Admin admin = adminService.findByMatriculeAndPassword(matriculeAdmin, adminPassword);
            session.setAttribute("admin", admin);
            return "redirect:/admin";
        }
        model.addAttribute("message", "Erreur de connexion");
        return "login";
    }

    @PostMapping("login/adherent")
    public String connexionAdherent(@RequestParam("adherent-matricule") int matriculeAdherent,
            @RequestParam("adherent-password") String adherentPassword,
            HttpSession session, Model model) {

        if (adherentService.isAdherent(matriculeAdherent, adherentPassword)) {
            Adherent adherent = adherentService.findByMatriculeAndPassword(matriculeAdherent, adherentPassword);
            session.setAttribute("adherent", adherent);
            return "redirect:/adherent";
        }
        model.addAttribute("message", "Erreur de connexion");
        return "login";
    }

}
