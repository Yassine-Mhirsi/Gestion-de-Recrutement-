package tn.pi.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.pi.entities.Company;
import org.springframework.stereotype.Service;
import tn.pi.repos.CompanyRepo;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyService {

    private final CompanyRepo companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyService(CompanyRepo companyRepository, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Fetch companies with their jobs
    @Transactional
    public List<Company> getAllCompaniesWithJobs() {
        return companyRepository.findAllWithJobs();
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public void saveCompany(Company company) {
        company.setPassword(passwordEncoder.encode(company.getPassword()));  // Password is hashed before saving
        companyRepository.save(company);
    }


    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }


}
