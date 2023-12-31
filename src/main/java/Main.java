import entity.Member;
import entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    static EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("jpa_lab_4");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx1 = em.getTransaction();

        tx1.begin();

        //
        testSave(em);

        // 객체지향 쿼리 사용 - jpql 조회
        queryLogicJoin(em);

        // 수정
        updateRelation(em);

        // 연관관계 제거
        deleteRelation(em);

        // 일대다 컬렉션 조회
        biDirection(em);

        tx1.commit();
        em.close();
    }

    private static void testSave(EntityManager em) {

        // 팀 1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원 1 저장
        Member member1 = new Member("member1", "회원1");
        // 연관관계 설정(연관관계의 주인)
        member1.setTeam(team1);
        em.persist(member1);

        // 객체 그래프 탐색 조회 (객체 연관관계를 사용한 조회)
//        Member member = em.find(Member.class, "member1");
//        Team team = member.getTeam();

        // 회원 2 저장
        Member member2 = new Member("member2", "회원2");
        // 연관관계 설정(연관관계의 주인)
        member2.setTeam(team1);
        em.persist(member2);
    }

    private static void queryLogicJoin(EntityManager em) {

        String jpql = "select m from Member m join m.team t where " +
                "t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username=" +
                    member.getUsername());
        }
    }

    private static void updateRelation(EntityManager em) {

        // 새로운 팀2
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        // 회원1에 새로운 팀2 설정
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(team2);
    }

    private static void deleteRelation(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null); // 연관관계 제거
    }

    // 일대다 방향으로 객체 그래프 탐색
    private static void biDirection(EntityManager em) {

        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers(); // (팀 -> 회원) 객체 그래프 탐색

        for (Member member: members) {
            System.out.println("member.username = " +
                        member.getUsername());
        }
    }

    // 양방향 연관관계 주의점
    // 연관관계의 주인이 아닌 곳에만 값을 입력하면, DB에 값이 정상적으로 저장되지 않는다.
    public static void testSaveNoOwner(EntityManager em) {

        // 회원 1 저장
        Member member3 = new Member("member3", "회원3");
        em.persist(member3);

        // 회원 2 저장
        Member member4 = new Member("member4", "회원4");
        em.persist(member4);

        Team team2 = new Team("team2", "팀2");

        // 주인이 아닌 곳만 연관관계 설정 > member3, member4의 TEAM_ID가 null.
        team2.getMembers().add(member3);
        team2.getMembers().add(member4);

        em.persist(team2);
    }
}
