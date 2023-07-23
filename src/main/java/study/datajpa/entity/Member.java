package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"})
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_idx")
    private Long id;
    private String username;
    private int age;
    @JoinColumn(name = "team_idx")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    //프록시 기술 쓸때 private 하면 다 막혀서

    public Member(String username) {
        this.username = username;
    }

    public Member(String member, int age, Team team) {
        this.username = member;
        this.age = age;
        if(team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
