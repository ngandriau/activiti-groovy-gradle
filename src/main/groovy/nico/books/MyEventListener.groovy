package nico.books

import groovy.util.logging.Slf4j
import org.activiti.engine.delegate.event.ActivitiActivityEvent
import org.activiti.engine.delegate.event.ActivitiEntityEvent
import org.activiti.engine.delegate.event.ActivitiEvent
import org.activiti.engine.delegate.event.ActivitiEventListener
import static org.activiti.engine.delegate.event.ActivitiEventType.*

/**
 * Created by ngandriau on 4/27/14.
 */
@Slf4j
class MyEventListener implements ActivitiEventListener {

    @Override
    void onEvent(ActivitiEvent event) {



        switch(event.type){

            case ENTITY_CREATED:
                ActivitiEntityEvent theEvent = (ActivitiEntityEvent)event
                log.debug "[EVENT] entity created of type: ${theEvent.entity.getClass()}"
                break

            case ACTIVITY_STARTED:
                ActivitiActivityEvent theEvent = (ActivitiActivityEvent)event
                log.debug "[EVENT] activity(${theEvent.activityId}) STARTED : "
                break
            case ACTIVITY_COMPLETED:
                ActivitiActivityEvent theEvent = (ActivitiActivityEvent)event
                log.debug "[EVENT] activity(${theEvent.activityId}) COMPLETED : "
                break

            default:
                log.debug "onEvent($event.type) - not yet handled"
        }
    }

    @Override
    boolean isFailOnException() {
        log.warn "isFailOnException()"
        return false
    }
}
