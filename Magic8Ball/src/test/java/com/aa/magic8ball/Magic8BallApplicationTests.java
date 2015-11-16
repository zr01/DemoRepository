package com.aa.magic8ball;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Magic8BallApplication.class)
@WebAppConfiguration
public class Magic8BallApplicationTests {

	@Test
	public void contextLoads() {
	}

}
