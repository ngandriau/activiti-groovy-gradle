Simple activiti project using gradle and groovy
===============================================

*   Activiti is initialized in a spring context (spring-activiti-context.xml), using the datasource and the transacion manager declared in the spring context.
    * Transaction log done by `org.springframework.jdbc.datasource.DataSourceTransactionManager`

*    simple process definition based on the book order from activiti in action `bookorder.bpmn20.xml`

    *   the second `service task` has an expression which use a bean declared in the spring context: `PrinterBean`

*   Use groovy 2.2.2, logback and mysql

*   1 Event listener configured

TODO
====

Integrate hibernate
-------------------
[forum which can help](http://forums.activiti.org/content/activiti-hibernate)

