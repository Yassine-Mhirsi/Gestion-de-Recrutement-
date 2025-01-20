package tn.pi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tn.pi.entities.Application;
import tn.pi.entities.Job;
import tn.pi.repos.JobRepo;
import tn.pi.service.ApplicationService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class JobController {
    private final JobRepo jobRepo;


    public JobController(JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }

    @GetMapping("/jobs")
    public String index(Model model,
                        @RequestParam(name = "page" , defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword
    ){
        Page<Job> jobs = jobRepo.findByTitleContainsIgnoreCase(keyword, PageRequest.of(page,size));
        model.addAttribute("jobs", jobs.getContent());
        model.addAttribute("pages", new int [jobs.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "jobs";
    }

    @GetMapping("/jobs/details/{id}")
    public String jobDetails(@PathVariable("id") Long id, Model model) {
        Job job = jobRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid job ID: " + id));
        model.addAttribute("job", job);
        return "job-details";
    }

    @GetMapping("/apply/{id}")
    public String showApplicationForm(@PathVariable Long id, Model model) {
        // Fetch the job by ID, including its associated company details
        Job job = jobRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job id: " + id));

        // Add the job details to the model
        model.addAttribute("job", job);
        model.addAttribute("requiredSkills", job.getRequiredSkills());

        return "apply"; // returns the apply.html view
    }

    @Autowired
    private ApplicationService applicationService;


    private String resumePath; // Store the file path

    @PostMapping("/apply/{id}")
    public String submitApplication(@PathVariable("id") Long id,
                                    @RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam String phone,
                                    @RequestParam String residence,
                                    @RequestParam List<String> skills,
                                    @RequestParam MultipartFile resume,
                                    @RequestParam String message) throws IOException {

        // Define the external path where the resume will be stored
        String uploadDir = "C:/Users/Yassine/Documents/J2E/TP/recrutment/src/main/uploads/resumes/"; // Or any other directory suitable for file uploads

        // Create the directory if it doesn't exist
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the "resumes" directory if it doesn't exist
        }

        // Define the resume file path
        String resumeFilename = resume.getOriginalFilename();
        String resumePath = "resumes/" + resumeFilename; // This is the relative path to save in DB

        // Save the resume file in the designated directory
        File resumeFile = new File(uploadDir + resumeFilename);
        resume.transferTo(resumeFile);

        // Process the application data
        Application application = new Application();
        application.setJobId(id);
        application.setName(name);
        application.setEmail(email);
        application.setPhone(phone);
        application.setResidence(residence);
        application.setSkills(skills);
        application.setResume(resumePath); // Save the relative path of the resume
        application.setMessage(message);

        // Save the application in the database
        applicationService.save(application);

        return "redirect:/jobs"; // Redirect to jobs listing after submission
    }







}
