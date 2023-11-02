package com.subaxpress.lib.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;

/**
 * Clase base que permite la creación de servicioReqs destinados al envío de
 * mensajes a una cola de RabbitMQ.
 */
public abstract class BaseQueueProducer {

    /**
     * Clase que permite el acceso síncrono a mensajes en RabbitMQ.
     */
    protected final RabbitTemplate rabbitTemplate;

    /**
     * Inyección de dependencias.
     *
     * @param rabbitTemplate clase que permite el acceso a mensajes en RabbitMQ
     */
    protected BaseQueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Prepara el objeto recibido para enviar a RabbitMQ como JSON estableciendo
     * el valor de la propiedad {@code Content-Type} como
     * {@code application/json}.
     *
     * @param payload objeto que se desea incluir en el mensaje de tipo JSON
     * @return mensaje con la propiedad establecida como
     * {@code application/json}
     * @throws JsonProcessingException no se pudo convertir objeto a JSON
     */
    protected static Message messageAsJSON(Object payload)
            throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer()
                .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(payload);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new Message(json.getBytes(), messageProperties);
    }
}
