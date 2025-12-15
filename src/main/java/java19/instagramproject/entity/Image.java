package java19.instagramproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String imageUrl;
/**
 * @OneToOne
 * @JoinColumn(name = "user_id",unique = true)
 * User user;
 */
@ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
}
