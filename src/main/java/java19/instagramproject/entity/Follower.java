package java19.instagramproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Followers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ElementCollection
    List<Long> subscribers =  new ArrayList<>();
    @ElementCollection
    List<Long> subscriptions = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    User user;
}
