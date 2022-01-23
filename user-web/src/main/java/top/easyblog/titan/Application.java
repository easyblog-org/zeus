package top.easyblog.titan;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableFeignClients
@EnableTransactionManagement
@MapperScans({
        @MapperScan("top.easyblog.titan.dao"),
        @MapperScan("top.easyboot.dao.auto")
})
@SpringBootApplication(scanBasePackages = {"top.easyblog.titan"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
