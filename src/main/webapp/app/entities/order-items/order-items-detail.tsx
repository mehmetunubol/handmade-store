import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-items.reducer';
import { IOrderItems } from 'app/shared/model/order-items.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderItemsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrderItemsDetail = (props: IOrderItemsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { orderItemsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          OrderItems [<b>{orderItemsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{orderItemsEntity.quantity}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{orderItemsEntity.price}</dd>
          <dt>Product</dt>
          <dd>{orderItemsEntity.product ? orderItemsEntity.product.id : ''}</dd>
          <dt>Cart</dt>
          <dd>{orderItemsEntity.cart ? orderItemsEntity.cart.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-items" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-items/${orderItemsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ orderItems }: IRootState) => ({
  orderItemsEntity: orderItems.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderItemsDetail);
