package tn.pi.repos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.pi.entities.Job;



public interface JobRepo
        extends JpaRepository<Job, Long>{
    Page<Job> findByTitleContainsIgnoreCase(String keyword, Pageable pageable);
}
