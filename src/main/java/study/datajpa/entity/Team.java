package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","name"})
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_idx")
    private Long id;
    private String name;
    // foregin key카 없는 쪽에 mapped by 권장됨
    @OneToMany(mappedBy = "team")
    @Column(name = "member_idx")
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

}
