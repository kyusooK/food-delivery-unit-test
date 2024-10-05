package fooddelivery.infra;

import fooddelivery.config.kafka.KafkaProcessor;
import fooddelivery.domain.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OrderHistoryViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderPlaced_then_CREATE_1(
        @Payload OrderPlaced orderPlaced
    ) {
        try {
            if (!orderPlaced.validate()) return;

            // view 객체 생성
            OrderHistory orderHistory = new OrderHistory();
            // view 객체에 이벤트의 Value 를 set 함
            orderHistory.setUserId(orderPlaced.getUserId());
            orderHistory.setOrderId(String.valueOf(orderPlaced.getId()));
            orderHistory.setStoreId(orderPlaced.getStoreId());
            orderHistory.setTotalAmount(orderPlaced.getTotalAmount());
            orderHistory.setStatus(String.valueOf(orderPlaced.getStatus()));
            // view 레파지 토리에 save
            orderHistoryRepository.save(orderHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderAccepted_then_UPDATE_1(
        @Payload OrderAccepted orderAccepted
    ) {
        try {
            if (!orderAccepted.validate()) return;
            // view 객체 조회

            List<OrderHistory> orderHistoryList = orderHistoryRepository.findByOrderId(
                orderAccepted.getOrderId()
            );
            for (OrderHistory orderHistory : orderHistoryList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderHistory.setStatus(
                    String.valueOf(orderAccepted.getStatus())
                );
                // view 레파지 토리에 save
                orderHistoryRepository.save(orderHistory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryStarted_then_UPDATE_2(
        @Payload DeliveryStarted deliveryStarted
    ) {
        try {
            if (!deliveryStarted.validate()) return;
            // view 객체 조회

            List<OrderHistory> orderHistoryList = orderHistoryRepository.findByOrderId(
                deliveryStarted.getOrderId()
            );
            for (OrderHistory orderHistory : orderHistoryList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderHistory.setRiderId(deliveryStarted.getRiderId());
                // view 레파지 토리에 save
                orderHistoryRepository.save(orderHistory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCompleted_then_UPDATE_3(
        @Payload DeliveryCompleted deliveryCompleted
    ) {
        try {
            if (!deliveryCompleted.validate()) return;
            // view 객체 조회

            List<OrderHistory> orderHistoryList = orderHistoryRepository.findByOrderId(
                deliveryCompleted.getOrderId()
            );
            for (OrderHistory orderHistory : orderHistoryList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                orderHistory.setStatus(deliveryCompleted.getStatus());
                // view 레파지 토리에 save
                orderHistoryRepository.save(orderHistory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryStarted_then_DELETE_1(
        @Payload DeliveryStarted deliveryStarted
    ) {
        try {
            if (!deliveryStarted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            orderHistoryRepository.deleteByRiderId(
                deliveryStarted.getRiderId()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
