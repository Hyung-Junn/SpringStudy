package study.data_jpa.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
    public Member(String username) {
        this.username = username;
    }

    protected Member() { //JPA는 기본적으로 디폴트 생성자가 필요함(프록시 구현체 쓸 때 기본 생성자써서 아예 막으면 안됨
    }

    @Id
    @GeneratedValue
    private Long id;

    private String username;
}
