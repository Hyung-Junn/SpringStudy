package hello.jdbc.service;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;

/*
    기본동작, 트랜잭션이 없어서 문제 발생
 */
class MemberServiceV1Test {

    private MemberServiceV1 memberService;
    private MemberRepositoryV1 memberRepository;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSoruce = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSoruce);
        memberService = new MemberServiceV1(memberRepository);
    }

    @AfterEach
    void after() throws SQLException {
        memberRepository.delete("memberA");
        memberRepository.delete("memberB");
        memberRepository.delete("memberEX");
    }

    @Test
    @DisplayName("정상 이체")
    void accointTransferEX() throws SQLException {
        //given
        Member memberA = new Member("memberA", 10000);
        Member memberEX = new Member("ex", 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEX);

        //when
        memberService.accountTransfer(memberA.getMemberId(), memberEX.getMemberId(), 2000);

        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberEX.getMemberId());

        Assertions.assertThat(findMemberA.getMoney()).isEqualTo(8000);
        Assertions.assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }
}