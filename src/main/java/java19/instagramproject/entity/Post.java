package java19.instagramproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "Posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Comment> comments =  new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Like> likes =new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Image> images =new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<User> taggedUsers = new ArrayList<>();
}
