package tn.pi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "job", uniqueConstraints = {
        @UniqueConstraint(columnNames = "title")
})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String title;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(nullable = false)
    private String employmentType; // e.g., "Contract" or "Full-time"

    @Column(name = "pay_class", length = 50)
    private String payClass; // e.g., "Contractor"

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(columnDefinition = "TEXT")
    private String companyDescription;

    @Column(columnDefinition = "TEXT")
    private String jobDescription;

    @Column(columnDefinition = "TEXT")
    private String responsibilities;

    @Column(columnDefinition = "TEXT")
    private String qualifications;

    @Column(columnDefinition = "TEXT")
    private String additionalInformation;

    private double minSalary;
    private double maxSalary;

    @Transient
    private List<Application> applications;

    @ElementCollection
    @CollectionTable(name = "job_required_skills", joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"))
    @Column(name = "skill")
    private List<String> requiredSkills;

}
