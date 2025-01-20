package tn.pi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.pi.entities.Company;

import java.util.List;

public interface CompanyRepo
        extends JpaRepository<Company, Long> {
    Company findByName(String name);
    Company findByUsername(String username);


    // Custom query to fetch companies with their jobs
    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.jobs")
    List<Company> findAllWithJobs();


}
