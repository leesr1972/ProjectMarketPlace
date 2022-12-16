package sky.pro.java.diplomproject.ProjectMarketPlace.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "avatars")
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String mediaType;
    @NonNull
    private String filePath;
    @NonNull
    private Long fileSize;

    @Lob
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "users_id")
    private Users users;
}
