package kr.co.greengram.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Feed extends UpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedId;

    @Column(length = 30)
    private String location;

    @Column(length = 1_000)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "write_user_id", nullable = false)
    private User writerUser;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedPic> feedPics = new ArrayList<>(1);

    public void addFeedPics(List<String> picFileNames) {
        for (String picFileName : picFileNames) {
            FeedPicIds ids = new FeedPicIds(this.feedId, picFileName);
            FeedPic feedPic = new FeedPic(ids, this);

            this.feedPics.add(feedPic);
        }
    }
}
