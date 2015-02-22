package ch.qos.logback.classic.net;

import ch.qos.logback.classic.spi.ILoggingEvent;

import javax.jms.*;
import java.util.Map;

public class JMSPerformanceAppender extends JMSQueueAppender {

    /**
     * This method called by {@link AppenderBase#doAppend} method to do most
     * of the real appending work.
     */
    public void append(ILoggingEvent event) {
        if (!isStarted()) {
            return;
        }

        try {
            MapMessage msg = getQueueSession().createMapMessage();
            //ObjectMessage msg = queueSession.createObjectMessage();
            msg.setLong("timestamp", event.getTimeStamp());
            msg.setString("status", event.getArgumentArray()[0].toString());
            msg.setString("duration", event.getArgumentArray()[1].toString());
            msg.setString("signature", event.getArgumentArray()[2].toString());
            for(Map.Entry<String,String> mdc : event.getMDCPropertyMap().entrySet()) {
                msg.setString(mdc.getKey(), mdc.getValue());
            }
            queueSender.send(msg);
            successiveFailureCount = 0;
        } catch (Exception e) {
            successiveFailureCount++;
            if (successiveFailureCount > SUCCESSIVE_FAILURE_LIMIT) {
                stop();
            }
            addError("Could not send message in JMSQueueAppender [" + name + "].", e);

        }
    }


}
