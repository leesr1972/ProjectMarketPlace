package sky.pro.java.diplomproject.ProjectMarketPlace.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    private String text;

    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Ads ads;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;
}
