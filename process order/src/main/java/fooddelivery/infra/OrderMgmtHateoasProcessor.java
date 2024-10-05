package fooddelivery.infra;

import fooddelivery.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class OrderMgmtHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<OrderMgmt>> {

    @Override
    public EntityModel<OrderMgmt> process(EntityModel<OrderMgmt> model) {
        return model;
    }
}
