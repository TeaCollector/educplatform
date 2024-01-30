package ru.rtstudy.educplatformsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


/**
 * TODO:
 *  1. Прибраться в папке exception, распределить все ошибки по доменам
 *  2. Использовать HttpStatus вместо HttpStatusCode
 *  3. Не хватает ResponseBuilder (в Controller не должно быть логики)
 *  4. Доавить logs (info, debug, error). Оформленный в одном стиле
 *
**/
@SpringBootApplication
@EnableMethodSecurity
public class EducplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducplatformApplication.class, args);
    }

}
