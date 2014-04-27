package nico.books

import groovy.util.logging.Slf4j
import org.activiti.engine.*
import org.activiti.engine.runtime.ProcessInstance
import org.activiti.engine.task.Task

/**
 * Created by ngandriau on 4/27/14.
 */
@Slf4j
class MainApp {


    ProcessEngine processEngine
    RuntimeService runtimeService

    public static void main(String[] args) {
        log.info "==== MainApp.starting"

        MainApp app = new MainApp()

        app.initProcessEngine()

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
        processEngine = ProcessEngines.getDefaultProcessEngine()

        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("processes/bookorder.bpmn20.xml")
                .deploy()

        runtimeService = processEngine.getRuntimeService()

    }


    ProcessInstance executeNewBookOrderProcess() {
        log.info "executeNewBookOrderProcess()"

        def variableMap = [isbn: "123456"]
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("bookorder", variableMap)

        log.debug "  new proc started with id: ${processInstance.id} - from proc def: ${processInstance.processDefinitionId}"

        processInstance
    }

    void executeEveryUserTask() {
        log.info "executeEveryUserTask()"

        TaskService taskService = processEngine.getTaskService()
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("sales").list()

        log.debug "  found [${tasks.size()}] waiting user tasks. Complete them all:"

        for (Task task : tasks) {
            log.debug "    complete Task(${task.name}) with id(${task.id})"
            taskService.complete(task.id)
        }
    }

    void waitEndOfAnyProc() {
        log.info "waitEndOfAnyProc()"
        List<ProcessInstance> activeProcesses = queryActiveProcesses()
        while (activeProcesses) {
            log.debug "  found at least 1 active proc, wait..."
            Thread.sleep(200)
            activeProcesses = queryActiveProcesses()
        }
    }

    protected List<ProcessInstance> queryActiveProcesses() {
        log.info "queryActiveProcesses()"
        runtimeService.createProcessInstanceQuery().processDefinitionKey("bookorder").list()
    }
}
