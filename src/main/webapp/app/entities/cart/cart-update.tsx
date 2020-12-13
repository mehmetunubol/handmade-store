import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClientDetails } from 'app/shared/model/client-details.model';
import { getEntities as getClientDetails } from 'app/entities/client-details/client-details.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cart.reducer';
import { ICart } from 'app/shared/model/cart.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICartUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CartUpdate = (props: ICartUpdateProps) => {
  const [clientDetailsId, setClientDetailsId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { cartEntity, clientDetails, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/cart' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClientDetails();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.placedDate = convertDateTimeToServer(values.placedDate);

    if (errors.length === 0) {
      const entity = {
        ...cartEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="storeApp.cart.home.createOrEditLabel">Create or edit a Cart</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : cartEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="cart-id">ID</Label>
                  <AvInput id="cart-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="notesLabel" for="cart-notes">
                  Notes
                </Label>
                <AvField id="cart-notes" type="text" name="notes" />
              </AvGroup>
              <AvGroup>
                <Label id="placedDateLabel" for="cart-placedDate">
                  Placed Date
                </Label>
                <AvInput
                  id="cart-placedDate"
                  type="datetime-local"
                  className="form-control"
                  name="placedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.cartEntity.placedDate)}
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="cart-status">
                  Status
                </Label>
                <AvInput
                  id="cart-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && cartEntity.status) || 'PENDING'}
                >
                  <option value="PENDING">PENDING</option>
                  <option value="WAITPAYMENT">WAITPAYMENT</option>
                  <option value="WAITPAYCONFIRM">WAITPAYCONFIRM</option>
                  <option value="WAITSHIP">WAITSHIP</option>
                  <option value="DECLINED">DECLINED</option>
                  <option value="SHIPPING">SHIPPING</option>
                  <option value="COMPLETED">COMPLETED</option>
                  <option value="RETURNSHIPPING">RETURNSHIPPING</option>
                  <option value="RETURNED">RETURNED</option>
                  <option value="CANCELLED">CANCELLED</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="totalPriceLabel" for="cart-totalPrice">
                  Total Price
                </Label>
                <AvField
                  id="cart-totalPrice"
                  type="text"
                  name="totalPrice"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    min: { value: 0, errorMessage: 'This field should be at least 0.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="paymentMethodLabel" for="cart-paymentMethod">
                  Payment Method
                </Label>
                <AvInput
                  id="cart-paymentMethod"
                  type="select"
                  className="form-control"
                  name="paymentMethod"
                  value={(!isNew && cartEntity.paymentMethod) || 'CREDIT_CARD'}
                >
                  <option value="CREDIT_CARD">CREDIT_CARD</option>
                  <option value="TRANSFER">TRANSFER</option>
                  <option value="HAND_PAY">HAND_PAY</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="cart-clientDetails">Client Details</Label>
                <AvInput id="cart-clientDetails" type="select" className="form-control" name="clientDetails.id">
                  <option value="" key="0" />
                  {clientDetails
                    ? clientDetails.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/cart" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  clientDetails: storeState.clientDetails.entities,
  cartEntity: storeState.cart.entity,
  loading: storeState.cart.loading,
  updating: storeState.cart.updating,
  updateSuccess: storeState.cart.updateSuccess,
});

const mapDispatchToProps = {
  getClientDetails,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CartUpdate);
