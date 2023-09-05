package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    // == 추가 ==
    // 일대다 관계를 매핑하기 위해 사용
    // mappedBy : 양방향 매핑일 때 사용, 반대쪽 매핑의 필드 이름을 속성값으로 사용
    // mappedBy 속성을 사용하여 연관관계의 주인이 아님을 설정
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();

    public Team() {
    }

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return this.members;
    }
}
