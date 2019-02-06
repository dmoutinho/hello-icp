package br.com.dmoutinho.helloicp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
public class HelloIcpApplication {
	public static void main(String[] args) {
		SpringApplication.run(HelloIcpApplication.class, args);
	}
}

@RestController
//@RequestMapping("/hello-icp")
@RequestMapping("/")
class HelloICPController {
    
	private String getAllIPs() throws SocketException {
		String all = "";
		Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
		while(e.hasMoreElements()) {
		    NetworkInterface n = (NetworkInterface) e.nextElement();
		    Enumeration<?> ee = n.getInetAddresses();
		    while (ee.hasMoreElements()) {
		        InetAddress i = (InetAddress) ee.nextElement();
		        //System.out.println(i.getHostAddress());
		        all += i.getHostAddress() + "<br>";
		    }
		}
		return all;
	}
	
	@GetMapping("")
    String findAll() {
    	String str = "v1.0.2 - Hello container: <br>";
    	try {
			//str += InetAddress.getLocalHost().getHostAddress();
			str += getAllIPs();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return str;
    }
    
}
