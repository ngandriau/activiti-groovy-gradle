package nico.books

import groovy.util.logging.Slf4j
import org.activiti.engine.ProcessEngine
import org.activiti.engine.RepositoryService
import org.activiti.engine.RuntimeService
import org.activiti.engine.TaskService
import org.activiti.engine.runtime.ProcessInstance
import org.activiti.engine.task.Task

@Slf4j
abstract class AbstractApp {
    ProcessEngine processEngine
    RuntimeService runtimeService

    /**
     * Objective is to initialize @processEngine and @runtimeService
     */
    abstract void initProcessEngine()

    void deployOrderProcess(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource("processes/bookorder.bpmn20.xml")
                .deploy()
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
            log.debug "  found ${activeProcesses.size()} active proc, wait..."
            Thread.sleep(200)
            activeProcesses = queryActiveProcesses()
        }
    }

    protected List<ProcessInstance> queryActiveProcesses() {
        log.info "queryActiveProcesses()"
        runtimeService.createProcessInstanceQuery().processDefinitionKey("bookorder").list()
    }
}
