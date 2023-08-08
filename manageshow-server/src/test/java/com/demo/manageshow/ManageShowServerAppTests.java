package com.demo.manageshow;

import com.demo.manageshow.controller.ShowController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ManageShowServerAppTests {
	@Value("${local.server.port}")
	private int port;
	@Autowired
	private ShowController showController;

	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	void contextLoads(){
		assertNotNull(showController,"Context is not initialized well. showController is null");
	}
	@Test
	public void addShowTest(){
		System.out.println(restTemplate.getForObject("http://localhost:" + port + "/shows",String.class));
	}

}
