package br.com.dmoutinho.helloicp;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloIcpApplicationTest {
	
	@Test
	public void whenGetVersion() {
	
		// given
		Version version = new Version("SNAPSHOT");
		
		// when
		String v = "1.0.0";
		version.setVersion(v);

		// then
		assertTrue(version.getVersion().equals(v));		
	
	}

}
