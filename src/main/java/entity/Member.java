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
    public void setTeam(Team team) {
        this.team = team;
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
