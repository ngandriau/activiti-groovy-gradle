package nico.books

import groovy.util.logging.Slf4j
import nico.books.beans.PrinterBean
import org.activiti.engine.runtime.ProcessInstance
import org.springframework.context.support.ClassPathXmlApplicationContext

@Slf4j
class SpringApp extends AbstractApp{

    public static void main(String[] args) {


        SpringApp app = new SpringApp()

        app.initProcessEngine()

        app.deployOrderProcess()

        List<ProcessInstance> activeProcesses = app.queryActiveProcesses()
        if (!activeProcesses)
            app.executeNewBookOrderProcess()

        app.executeEveryUserTask()

        app.waitEndOfAnyProc()

        log.info "==== MainApp.Over"

        app.processEngine.close()
    }

    void initProcessEngine() {
        log.info "initProcessEngine()"

        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring-activiti-context.xml");

        processEngine = applicationContext.getBean("processEngine")

        runtimeService = processEngine.getRuntimeService()

    }


}
