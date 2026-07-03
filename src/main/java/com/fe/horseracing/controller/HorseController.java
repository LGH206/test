package com.fe.horseracing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fe.horseracing.pojo.Horse;
import com.fe.horseracing.service.interfaces.IHorseService;

@Controller
@RequestMapping("/horses")
public class HorseController {

    @Autowired
    private IHorseService horseService;

    // LIST
    @GetMapping
    public String listHorse(Model model) {

        model.addAttribute("horses",
                horseService.findAll());

        return "horses/list";
    }

    // PROFILE
    @GetMapping("/{id}")
    public String horseProfile(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("horse",
                horseService.findById(id));

        return "horses/detail";
    }

    // ADD FORM
    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("horse",
                new Horse());

        return "horses/form";
    }

    // SAVE
    @PostMapping("/save")
    public String saveHorse(
            @ModelAttribute Horse horse) {

        horseService.save(horse);

        return "redirect:/horses";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String editHorse(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("horse",
                horseService.findById(id));

        return "horses/form";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteHorse(
            @PathVariable Long id) {

        horseService.delete(id);

        return "redirect:/horses";
    }
}