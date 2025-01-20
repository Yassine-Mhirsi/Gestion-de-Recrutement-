package tn.pi.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    private String name;
    private String email;
    private String phone;
    private String residence;

    @ElementCollection
    @CollectionTable(name = "application_skills", joinColumns = @JoinColumn(name = "application_id"))
    @Column(name = "skill")
    private List<String> skills;

    private String resume; // You can store the resume as a file path or save it as a byte array
    private String message;
}


