package java19.instagramproject.entity;

import jakarta.persistence.*;
import java19.instagramproject.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userName;
    String password;
    String email;
    String phoneNumber;
    @Enumerated(EnumType.STRING)
    Role role;

    public String getUserName() {
        return userName;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    UserInfo userInfo;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Follower  follower;
    @OneToMany(mappedBy = "user")
    List<Post> posts =  new ArrayList<>();
    @OneToMany(mappedBy = "user")
    List<Comment> comments =  new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }


    //    @OneToOne(mappedBy = "user")
//    Like like;
/*
    @OneToOne(mappedBy = "user")
    Image image;
*/
}
