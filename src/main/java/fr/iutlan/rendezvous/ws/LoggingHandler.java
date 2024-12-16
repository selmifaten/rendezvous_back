package fr.iutlan.rendezvous.ws;

import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {
    private static final Logger logger = LoggerFactory.getLogger(LoggingHandler.class);

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        SOAPMessage msg = context.getMessage();
        logger.info(isOutbound ? "Outbound message:" : "Inbound message:");
        try {
            msg.writeTo(System.out);
            logger.info("SOAP Message logged successfully.");
        } catch (Exception e) {
            logger.error("Error logging SOAP message: ", e);
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logger.error("SOAP fault encountered.");
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
