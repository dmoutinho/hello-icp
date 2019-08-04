package br.com.dmoutinho.helloicp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

	@GetMapping("/ip-client")
	ResponseEntity<String> ipClient(HttpServletRequest req) {
		ResponseEntity<String> responseEntity = null;
    	try {
    		responseEntity = new ResponseEntity<String>(req.getRemoteAddr(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return responseEntity;
    }

	@GetMapping("/headers")
	ResponseEntity<List<Header>> headers(HttpServletRequest req) {
		ResponseEntity<List<Header>> responseEntity = null;
    	try {
    		
    		List<Header> headers = new ArrayList<>();
    		Enumeration<String> headersEnum = req.getHeaderNames();
    		while( headersEnum.hasMoreElements() ) {
    			String name = headersEnum.nextElement();
    			headers.add(new Header(name,req.getHeader(name)));
    		}
    		
    		responseEntity = new ResponseEntity<List<Header>>(headers,HttpStatus.OK);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
        return responseEntity;
    }	
	
	@GetMapping("/version")
	ResponseEntity<Version> version() {
		ResponseEntity<Version> responseEntity = null;
    	try {
    		responseEntity = new ResponseEntity<Version>(new Version(this.version),HttpStatus.OK);
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

class Header {
	private String name;
	private String value;
	public Header(String name, String value) {
		this.name = name;
		this.value = value;		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
