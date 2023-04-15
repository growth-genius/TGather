package com.ysdeveloper.tgather.infra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@ActiveProfiles( "test" )
@DataJpaTest
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
public @interface RepositoryTest {

}
