import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cart.reducer';
import { ICart } from 'app/shared/model/cart.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICartDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CartDetail = (props: ICartDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cartEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Cart [<b>{cartEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="notes">Notes</span>
          </dt>
          <dd>{cartEntity.notes}</dd>
          <dt>
            <span id="placedDate">Placed Date</span>
          </dt>
          <dd>{cartEntity.placedDate ? <TextFormat value={cartEntity.placedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{cartEntity.status}</dd>
          <dt>
            <span id="totalPrice">Total Price</span>
          </dt>
          <dd>{cartEntity.totalPrice}</dd>
          <dt>
            <span id="paymentMethod">Payment Method</span>
          </dt>
          <dd>{cartEntity.paymentMethod}</dd>
          <dt>Client Details</dt>
          <dd>{cartEntity.clientDetails ? cartEntity.clientDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cart" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cart/${cartEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cart }: IRootState) => ({
  cartEntity: cart.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CartDetail);
