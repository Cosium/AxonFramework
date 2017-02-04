package org.axonframework.spring.messaging;

import org.axonframework.eventhandling.EventMessage;
import org.springframework.messaging.Message;

/**
 * Created on 04/02/17.
 *
 * @author Reda.Housni-Alaoui
 */
public interface EventMessageConverter {

	/**
	 * Converts an Event message into an outbound message
	 *
	 * @param event The event to convert
	 * @param <T>   The event payload type
	 * @return The outbound message
	 */
	<T> Message<T> convertToOutboundMessage(EventMessage<T> event);

	/**
	 * Converts a inbound message into an Event Message
	 *
	 * @param message The message to convert
	 * @param <T>     The message payload type
	 * @return The event message
	 */
	<T> EventMessage<T> convertFromInboundMessage(Message<T> message);
}
