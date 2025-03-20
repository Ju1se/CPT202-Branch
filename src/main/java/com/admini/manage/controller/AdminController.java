package com.admini.manage.controller;

import com.admini.manage.entity.ReadingLog;
import com.admini.manage.entity.User;
import com.admini.manage.service.ReadingLogService;
import com.admini.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final ReadingLogService readingLogService;

    @Autowired
    public AdminController(UserService userService, ReadingLogService readingLogService) {
        this.userService = userService;
        this.readingLogService = readingLogService;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/{id}/lock")
    public String lockUser(@PathVariable Long id) {
        userService.lockUser(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/unlock")
    public String unlockUser(@PathVariable Long id) {
        userService.unlockUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/reading-logs")
    public String getAllReadingLogs(Model model) {
        model.addAttribute("readingLogs", readingLogService.getAllReadingLogs());
        return "admin/reading-logs";
    }

    @PostMapping("/reading-logs/{id}/approve")
    public String approveReadingLog(@PathVariable Long id) {
        readingLogService.approveReadingLog(id);
        return "redirect:/admin/reading-logs";
    }

    @PostMapping("/reading-logs/{id}/reject")
    public String rejectReadingLog(@PathVariable Long id) {
        readingLogService.rejectReadingLog(id);
        return "redirect:/admin/reading-logs";
    }

    @PostMapping("/reading-logs/{id}/delete")
    public String deleteReadingLog(@PathVariable Long id) {
        readingLogService.deleteReadingLog(id);
        return "redirect:/admin/reading-logs";
    }
} 