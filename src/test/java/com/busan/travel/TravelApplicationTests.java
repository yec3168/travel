package com.busan.travel;

import com.busan.travel.page.entity.Board;
import com.busan.travel.page.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TravelApplicationTests {

	@Autowired
	private BoardService boardService;
	@Test
	void contextLoads() {
	}

	@Test
	void testJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			Board board = new Board();
			board.
			this.boardService.create(subject, content, null);
		}
	}

}
