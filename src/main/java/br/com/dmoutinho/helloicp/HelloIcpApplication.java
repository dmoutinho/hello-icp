package br.com.dmoutinho.helloicp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/")
class HelloICPController {
    
	@Value("${version}")
	private String version;
	
	private List<String> getAllIPs() throws SocketException {
		List<String> ips = new ArrayList<>();
		Enumeration<?> e = NetworkInterface.getNetworkInterfaces();
		while(e.hasMoreElements()) {
		    NetworkInterface n = (NetworkInterface) e.nextElement();
		    Enumeration<?> ee = n.getInetAddresses();
		    while (ee.hasMoreElements()) {
		        InetAddress i = (InetAddress) ee.nextElement();
		        ips.add(i.getHostAddress());
		    }
		}
		return ips;
	}
	
	@GetMapping("/ips")
	ResponseEntity<IPList> ips() {
    	//String str = "v1.0.4 - Hello container: <br>";
		ResponseEntity<IPList> responseEntity = null;
    	try {
    		IPList ipList = new IPList();
    		ipList.setIps(getAllIPs());
    		responseEntity = new ResponseEntity<IPList>(ipList,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return responseEntity;
    }

	@GetMapping("/healthz")
	ResponseEntity<String> healthz() {
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<String>("OK",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return responseEntity;
		}	
	}

	@GetMapping("")
	ResponseEntity<String> root() {
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<String>("OK",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return responseEntity;
		}	
	}

class Version {
	private String version;
	public Version(String version) {
		this.version = version;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}

class IPList {
	private List<String> ips = new ArrayList<>();
	public List<String> getIps() {
		return ips;
	}
	public void setIps(List<String> ips) {
		this.ips = ips;
	}	
}
