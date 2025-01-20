package tn.pi.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.pi.entities.Company;
import tn.pi.service.CompanyService;

import java.util.List;

@Controller
public class AdminDashboardController {
    private final CompanyService companyService;

    public AdminDashboardController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public String getCompanies(Model model) {
        List<Company> companies = companyService.getAllCompaniesWithJobs();
        model.addAttribute("companies", companies);
        model.addAttribute("newCompany", new Company());
        return "dashboard/companies";
    }


    @PostMapping("/dashboard/companies/add")
    public String addCompany(@ModelAttribute Company company) {
        companyService.saveCompany(company);
        return "redirect:/companies";
    }


    @GetMapping("/dashboard/company/delete/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }


}
