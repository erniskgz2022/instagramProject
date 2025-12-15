package java19.instagramproject.entity;

import jakarta.persistence.*;
import java19.instagramproject.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "UsersInfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String fullName;
    String biography;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String image;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    User user;
}
