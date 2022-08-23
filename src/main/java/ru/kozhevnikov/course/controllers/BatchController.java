package ru.kozhevnikov.course.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kozhevnikov.course.dao.PersonDAO;

@Controller
@RequestMapping("/test-batch-update")
public class BatchController {

    @Autowired
    private PersonDAO personDAO;

    @GetMapping()
    public String index() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String withoutBatchUpdate() {
        personDAO.testMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String withBatchUpdate() {
        personDAO.testBatchUpdate();
        return "redirect:/people";
    }

    @DeleteMapping("/batch-delete")
    public String batchDelete() {
        personDAO.testBatchDelete();
        return "redirect:/people";
    }
}
