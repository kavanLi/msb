package com.example.demo.thread;

import com.mashibing.internal.common.domain.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: kavanLi-R7000
 * @create: 2023-09-05 07:13
 * To change this template use File | Settings | File and Code Templates.
 */

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@RestController
public class Main {
    /* fields -------------------------------------------------------------- */


    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */
    public static void main(String[] args) {
        //Integer sharedInteger = new Integer(1000);
        //Integer sharedInteger2 = sharedInteger;
        //sharedInteger2++;
        //User sharedInteger1 = new User();
        //sharedInteger1.setId("asd");
        //sharedInteger1.setName("asd343");
        //sharedInteger1.setDate(new Date());
        //
        //User sharedInteger23 = sharedInteger1;
        //sharedInteger23.setId("333333333333");
        //new SpringApplication(Main.class).run(args);
    }


    @RequestMapping(value = "userSearch", method = RequestMethod.POST)
    public String userSearch(@RequestBody User user) {
        return "ok";
    }

    @RequestMapping(value = "userSearch1", method = RequestMethod.GET)
    public String userSearch() {
        return "ok";
    }
    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
