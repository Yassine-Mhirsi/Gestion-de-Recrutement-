package tn.pi.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/index";  // Admin dashboard
    }

//    @GetMapping("/company")
//    public String companyDashboard() {
//        return "company/index";
//    }
}
