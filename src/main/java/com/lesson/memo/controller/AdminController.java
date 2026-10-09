package com.lesson.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin/signup";
    }
    @PostMapping("/signup")
    public String signup(@ModelAttribute Admin admin,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            model.addAttribute("errorMessage", "このメールアドレスは既に登録されています。");
            return "admin/signup";
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);

        redirectAttributes.addFlashAttribute("successMessage", "登録が完了しました。ログインしてください。");
        return "redirect:/admin/signin";
    }
    @GetMapping("/signin")
    public String signin() {
        return "admin/signin";
    }
}