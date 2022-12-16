package sky.pro.java.diplomproject.ProjectMarketPlace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    @NonNull
    private Role role;
    @NonNull
    private String password;
    private Boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<Ads> ads;

    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    private String image;
}
