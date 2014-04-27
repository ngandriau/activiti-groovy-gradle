import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

def USER_HOME = System.getProperty("user.home");

appender("CONSOLE", ConsoleAppender) {
    println "USER_HOME is ${USER_HOME}"

    encoder(PatternLayoutEncoder) {
        pattern = "%-5level %logger{36} - %msg%n %xException"
//        pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
    }
}

root(INFO, ["CONSOLE"])


// Hibernate specific loggers
logger ("org.hibernate.SQL", DEBUG) //display show jpql queries
logger ("org.hibernate.type.descriptor.sql.BasicBinder", TRACE) //print bound parameters of the queries.
logger ("org.hibernate.hql.internal.ast.QueryTranslatorImpl", DEBUG) //print additional info on query.
logger ("org.hibernate", INFO)


// Transaction management
logger ("org.apache.ibatis.transaction.jdbc.JdbcTransaction", DEBUG)
logger ("org.activiti.engine.impl.cfg.standalone.StandaloneMybatisTransactionContext", DEBUG)


// TEST DEBUG
logger ("nico.books.MainApp", DEBUG)
//logger ("nico.books.MyEventListener", DEBUG)
