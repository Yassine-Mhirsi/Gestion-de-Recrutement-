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
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "logo_path", nullable = true)
    private String logoPath; // Path to the logo file on the server

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;

    @Column(nullable = false, unique = true)
    private String username; // Company username for login

    @Column(nullable = false)
    private String password; // Company password for login (hashed)


}
