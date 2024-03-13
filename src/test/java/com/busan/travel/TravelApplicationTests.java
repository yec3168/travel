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



}
