package entity;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    // 연관관계 매핑
    // 다대일 (N:1) 관계라는 매핑 정보
    // 연관관계 매핑시, 다중성을 나타내는 어노테이션을 필수로 사용
    // optional : false / true false 설정 시, 연관된 엔티티가 항상 있어야 함
    // 연관관계의 주인 (Readme 참조)
    // 다대일, 일대다 관계에서는 항상 다 쪽이 외래 키를 가진다.
    // 즉, 외래키를 갖게되어 연관관계의 주인이 된다.
    @ManyToOne
    // 조인 컬럼은 외래 키를 매핑할 때 사용한다.
    // name 속성은 매핑할 외래 키 이름을 지정한다. 생략 가능
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // 연관관계 설정
    // 연관관계 편의 메소드
    public void setTeam(Team team) {
        // 양방향 관계를 모두 설정하도록 변경
        // team.getMembers().add(this);

        // 기존 팀과 관계를 제거
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return this.team;
    }
}
