package tn.pi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.pi.entities.Application;
import tn.pi.entities.Job;

import java.util.List;


@Repository
public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

}

