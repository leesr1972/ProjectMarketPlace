package sky.pro.java.diplomproject.ProjectMarketPlace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    @NonNull
    private Integer price;
    @NonNull
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @OneToOne
    @JoinColumn(name = "images_id")
    private Images images;

    @JsonIgnore
    @OneToMany(mappedBy = "ads")
    private List<Comment> comments;
}
