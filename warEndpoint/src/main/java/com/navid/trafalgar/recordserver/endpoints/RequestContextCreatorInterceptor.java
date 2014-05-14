package com.navid.trafalgar.recordserver.endpoints;

import com.navid.trafalgar.recordserver.services.RequestContext;
import com.navid.trafalgar.recordserver.services.RequestContextContainer;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.cxf.headers.Header;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class RequestContextCreatorInterceptor extends AbstractPhaseInterceptor<Message> {

    @Resource
    private RequestContextContainer requestContextContainer;

    public RequestContextCreatorInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) {
        RequestContext requestContext = requestContextContainer.create();
        if(((Map<String, List<String>>)message.getContextualProperty(Message.PROTOCOL_HEADERS)).get("RID") != null) {
            requestContext.setRequestId(((Map<String, List<String>>)message.getContextualProperty(Message.PROTOCOL_HEADERS)).get("RID").get(0));
        } else {
            requestContext.setRequestId(UUID.randomUUID().toString());
        }
    }

    @Override
    public void handleFault(Message messageParam) {
        requestContextContainer.create();
    }
}
