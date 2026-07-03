package com.fe.horseracing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fe.horseracing.pojo.RaceResult;
import com.fe.horseracing.service.interfaces.IRaceResultService;

@Controller
@RequestMapping("/results")
public class RaceResultController {
	private IRaceResultService raceResultService;
	
    @Autowired
    public RaceResultController(IRaceResultService raceResultService) {
        this.raceResultService = raceResultService;
    }
    // List all results
    @GetMapping
    public String findAll(Model model) {

        model.addAttribute(
                "results",
                raceResultService.findAll());

        return "results/list";
    }

    // Find by ID
    @GetMapping("/{id}")
    public String findById(
            @PathVariable("id") Long id,
            Model model) {

        model.addAttribute(
                "result",
                raceResultService.findById(id));

        return "results/detail";
    }

    // Delete
    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable("id") Long id) {

        raceResultService.delete(id);

        return "redirect:/results";
    }
}
