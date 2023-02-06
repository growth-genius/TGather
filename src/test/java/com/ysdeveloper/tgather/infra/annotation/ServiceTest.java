package com.ysdeveloper.tgather.infra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
@ActiveProfiles( "test" )
@SpringBootTest
@Transactional
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
public @interface ServiceTest {}
