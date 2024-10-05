
package fooddelivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fooddelivery.config.kafka.KafkaProcessor;
import fooddelivery.domain.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderInfoTransferTest {

   private static final Logger LOGGER = LoggerFactory.getLogger(OrderInfoTransferTest.class);
   
   @Autowired
   private KafkaProcessor processor;
   @Autowired
   private MessageCollector messageCollector;
   @Autowired
   private ApplicationContext applicationContext;

   @Autowired
   public OrderMgmtRepository repository;

   @Test
   @SuppressWarnings("unchecked")
   public void test0() {

      //given:
   OrderMgmt entity = new OrderMgmt();

      entity.setId("1");
      entity.setOrderId("N/A");
      entity.setUserId("N/A");
      entity.setStoreId("N/A");
      entity.setTotalAmount("N/A");
      entity.setComment("N/A");
      entity.setAddress(new Object[]{[object Object]});
      entity.setOrderItems(new Object[]{[object Object]});
      entity.setCoupon("N/A");

      repository.save(entity);

      //when:  
      
      OrderPlaced event = new OrderPlaced();

      event.setId("N/A");
      event.setUserId("N/A");
      event.setStoreId("1");
      event.setTotalAmount("N/A");
      event.setAddress("N/A");
      event.setOrderItems("N/A");
      event.setStatus("N/A");
   
   
   OrderMgmtApplication.applicationContext = applicationContext;

      ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      try {
         String msg = objectMapper.writeValueAsString(event);

         processor.inboundTopic().send(
            MessageBuilder
            .withPayload(msg)
            .setHeader(
               MessageHeaders.CONTENT_TYPE,
               MimeTypeUtils.APPLICATION_JSON
            )
            .setHeader("type", event.getEventType())
            .build()
         );

         //then:

         Message<String> received = (Message<String>) messageCollector.forChannel(processor.outboundTopic()).poll();

         assertNotNull("Resulted event must be published", received);

         CouponAdded outputEvent = objectMapper.readValue((String)received.getPayload(), CouponAdded.class);


         LOGGER.info("Response received: {}", received.getPayload());

         assertEquals(String.valueOf(outputEvent.getId()), "N/A");
         assertEquals(String.valueOf(outputEvent.getOrderId()), "1");
         assertEquals(String.valueOf(outputEvent.getUserId()), "N/A");
         assertEquals(String.valueOf(outputEvent.getStoreId()), "N/A");
         assertEquals(String.valueOf(outputEvent.getTotalAmount()), "N/A");
         assertEquals(String.valueOf(outputEvent.getComment()), "N/A");
         assertEquals(String.valueOf(outputEvent.getOrderItems()), "N/A");
         assertEquals(String.valueOf(outputEvent.getStatus()), "N/A");
         assertEquals(String.valueOf(outputEvent.getAddress()), "N/A");
         assertEquals(String.valueOf(outputEvent.getCoupon()), "N/A");


      } catch (JsonProcessingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         assertTrue(e.getMessage(), false);
      }

     
   }

}
