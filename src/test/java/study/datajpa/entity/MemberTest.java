package study.datajpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.datajpa.repository.MemberRepository;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        //초기화
        em.flush();
        em.clear(); //jpa 영속성 컨텍스트 캐시를 날려버려

        //확인
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }

    }

    @Test
    public void JpaEventBasedEntity() throws InterruptedException {
        //given
        Member member = new Member("member1");
        memberRepository.save(member); //@PrePersit

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush(); //@PreUpdate
        em.clear();
        //when
        Member findMember = memberRepository.findById(member.getId()).get();

        //then
        System.out.println("findMember.getCreatedDate = " + findMember.getCreatedDate());
        System.out.println("findMember.getUpdatedDate = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getCreateBy() = " + findMember.getCreateBy());
        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());
    }
}