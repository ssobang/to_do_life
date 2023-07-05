package haza.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import haza.demo.domain.Member;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class JdbcMemberRepositoryTest {
	
	@Autowired JdbcMemberRepository repository;
	
	@Test
	void save() {
		Member member = new Member("홍길동", "hong", "1111");
		repository.memberSave(member);
	}

}
