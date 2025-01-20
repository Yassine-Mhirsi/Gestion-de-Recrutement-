package tn.pi.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.pi.entities.Application;
import tn.pi.entities.Company;
import tn.pi.entities.Job;
import tn.pi.repos.ApplicationRepository;
import tn.pi.repos.CompanyRepo;
import tn.pi.repos.JobRepo;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CompanyController {

    private final CompanyRepo companyRepo;
    private final JobRepo jobRepo;
    private final ApplicationRepository applicationRepository;

    public CompanyController(CompanyRepo companyRepo, JobRepo jobRepo, ApplicationRepository applicationRepository) {
        this.companyRepo = companyRepo;
        this.jobRepo = jobRepo;
        this.applicationRepository = applicationRepository;
    }

    @GetMapping("/company")
    public String companyDashboard(Model model) {
        // Get the logged-in user's username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find the company by username
        Company company = companyRepo.findByUsername(username);


        List<Job> jobs = company.getJobs();

        // Add company details to the model
        model.addAttribute("companyLogo", company.getLogoPath());
        model.addAttribute("companyName", company.getName());
        model.addAttribute("username", username);
        model.addAttribute("jobs", jobs);

        // Populate the applications for each job
        for (Job job : jobs) {
            List<Application> applications = applicationRepository.findAll().stream()
                    .filter(application -> application.getJobId().equals((long) job.getId()))
                    .toList();
            job.setApplications(applications);  // Set applications for each job
        }


        return "company/index";  // Return the appropriate view
    }

    @PostMapping("/company/addJob")
    public String addJob(
            @RequestParam String title,
            @RequestParam String location,
            @RequestParam String employmentType,
            @RequestParam double minSalary,
            @RequestParam double maxSalary,
            @RequestParam String companyDescription,
            @RequestParam String jobDescription,
            @RequestParam String responsibilities,
            @RequestParam String qualifications,
            @RequestParam(required = false) String additionalInformation,
            @RequestParam(required = false) List<String> predefinedSkills,
            @RequestParam(required = false) String customSkill) {

        // Get the logged-in company
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Company company = companyRepo.findByUsername(username);

        // Combine predefined and custom skills
        List<String> requiredSkills = new ArrayList<>();
        if (predefinedSkills != null) {
            requiredSkills.addAll(predefinedSkills);
        }
        if (customSkill != null && !customSkill.isEmpty()) {
            // Split the custom skills by commas and trim any spaces
            String[] customSkillsArray = customSkill.split(",");
            for (String skill : customSkillsArray) {
                requiredSkills.add(skill.trim());
            }
        }

        // Build and save the new job
        Job newJob = Job.builder()
                .title(title)
                .location(location)
                .employmentType(employmentType)
                .company(company)
                .companyDescription(companyDescription)
                .jobDescription(jobDescription)
                .responsibilities(responsibilities)
                .qualifications(qualifications)
                .additionalInformation(additionalInformation)
                .minSalary(minSalary)
                .maxSalary(maxSalary)
                .requiredSkills(requiredSkills)  // Set required skills in the Job entity
                .build();

        jobRepo.save(newJob);

        return "redirect:/company";
    }


    // Controller method to handle job deletion
    @RequestMapping(value = "/jobs/delete/{id}", method = RequestMethod.POST)
    public String deleteJob(@PathVariable Long id) {
        Job job = jobRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job id: " + id));

        // Delete the job
        jobRepo.delete(job);

        // Redirect to jobs listing page
        return "redirect:/company"; // Redirect to the jobs list or other page
    }






}

