package hello.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hello.CC;

import hello.service.Service;
import hello.service.ServiceConfiguration;

@SpringBootApplication
@Import(ServiceConfiguration.class)
@ComponentScan ({"com.hello", "hello.service"})
@RestController
public class DemoApplication {

    private final Service service;
    @Autowired
    private  CC cc;
    
/*    @Autowired
    private  ServiceCC servicec;*/

    @Autowired
    public DemoApplication(Service service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return service.message();
    }
    
    @GetMapping("/cc")
    public String homec() {
    	return cc.say();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
