package fooddelivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fooddelivery.config.kafka.KafkaProcessor;
import fooddelivery.domain.*;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMessageVerifier
public class PickTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(
        PickTest.class
    );

    @Autowired
    private KafkaProcessor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MessageVerifier<Message<?>> messageVerifier;

    @Autowired
    public DeliveryRepository repository;

    @Test
    @SuppressWarnings("unchecked")
    public void test0() {
        //given:
        Delivery existingEntity = new Delivery();

        existingEntity.setId("N/A");
        existingEntity.setOrderId("N/A");
        existingEntity.setUserId("N/A");
        existingEntity.setReceiverAddr("N/A");
        existingEntity.setRiderId("N/A");
        existingEntity.setStatus("N/A");

        repository.save(existingEntity);

        //when:

        try {
            Delivery newEntity = new Delivery();

            newEntity.setNA("N/A");

            repository.save(newEntity);

            this.messageVerifier.send(
                    MessageBuilder
                        .withPayload(newEntity)
                        .setHeader(
                            MessageHeaders.CONTENT_TYPE,
                            MimeTypeUtils.APPLICATION_JSON
                        )
                        .build(),
                    "fooddelivery"
                );

            Message<?> receivedMessage =
                this.messageVerifier.receive(
                        "fooddelivery",
                        5000,
                        TimeUnit.MILLISECONDS
                    );
            assertNotNull("Resulted event must be published", receivedMessage);

            //then:
            String receivedPayload = (String) receivedMessage.getPayload();

            DeliveryStarted outputEvent = objectMapper.readValue(
                receivedPayload,
                DeliveryStarted.class
            );

            LOGGER.info("Response received: {}", outputEvent);

            assertEquals(outputEvent.getId(), "N/A");
            assertEquals(outputEvent.getOrderId(), "N/A");
            assertEquals(outputEvent.getUserId(), "N/A");
            assertEquals(outputEvent.getReceiverAddr(), "N/A");
            assertEquals(outputEvent.getRiderId(), "N/A");
            assertEquals(outputEvent.getStatus(), "N/A");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            assertTrue(e.getMessage(), false);
        }
    }
}
