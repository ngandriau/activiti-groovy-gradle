package nico.books

import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * Created by ngandriau on 4/28/14.
 */
class SpringApp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("SpringTransactionIntegrationTest-context.xml");
    }

}
