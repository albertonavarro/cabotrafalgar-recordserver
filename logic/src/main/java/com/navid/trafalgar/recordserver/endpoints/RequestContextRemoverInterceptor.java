/*
 */
package com.navid.trafalgar.recordserver.endpoints;

import com.navid.trafalgar.recordserver.services.RequestContextContainer;
import javax.annotation.Resource;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class RequestContextRemoverInterceptor extends AbstractPhaseInterceptor<Message> {

    @Resource
    private RequestContextContainer requestContextContainer;

    public RequestContextRemoverInterceptor() {
        super(Phase.SEND);
    }

    @Override
    public void handleMessage(Message message) {
        requestContextContainer.delete();
    }

    @Override
    public void handleFault(Message messageParam) {
        requestContextContainer.delete();
    }

}
