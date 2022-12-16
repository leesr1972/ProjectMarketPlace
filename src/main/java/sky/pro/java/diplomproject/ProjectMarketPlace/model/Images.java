package sky.pro.java.diplomproject.ProjectMarketPlace.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String mediaType;
    @NonNull
    private String filePath;
    @NonNull
    private Long fileSize;

    @OneToOne
    @JoinColumn(name = "ads_id")
    private Ads ads;
}
