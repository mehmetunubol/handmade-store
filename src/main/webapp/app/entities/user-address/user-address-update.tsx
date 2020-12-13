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
import { ICart } from 'app/shared/model/cart.model';
import { getEntities as getCarts } from 'app/entities/cart/cart.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-address.reducer';
import { IUserAddress } from 'app/shared/model/user-address.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserAddressUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserAddressUpdate = (props: IUserAddressUpdateProps) => {
  const [clientDetailsId, setClientDetailsId] = useState('0');
  const [cartId, setCartId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { userAddressEntity, clientDetails, carts, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/user-address' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getClientDetails();
    props.getCarts();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...userAddressEntity,
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
          <h2 id="storeApp.userAddress.home.createOrEditLabel">Create or edit a UserAddress</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userAddressEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-address-id">ID</Label>
                  <AvInput id="user-address-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="user-address-name">
                  Name
                </Label>
                <AvField
                  id="user-address-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="user-address-type">
                  Type
                </Label>
                <AvField
                  id="user-address-type"
                  type="text"
                  name="type"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="detailLabel" for="user-address-detail">
                  Detail
                </Label>
                <AvField
                  id="user-address-detail"
                  type="text"
                  name="detail"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cityLabel" for="user-address-city">
                  City
                </Label>
                <AvField
                  id="user-address-city"
                  type="text"
                  name="city"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="countryLabel" for="user-address-country">
                  Country
                </Label>
                <AvField
                  id="user-address-country"
                  type="text"
                  name="country"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="user-address-clientDetails">Client Details</Label>
                <AvInput
                  id="user-address-clientDetails"
                  type="select"
                  className="form-control"
                  name="clientDetails.id"
                  value={isNew ? clientDetails[0] && clientDetails[0].id : userAddressEntity.clientDetails?.id}
                  required
                >
                  {clientDetails
                    ? clientDetails.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="user-address-cart">Cart</Label>
                <AvInput id="user-address-cart" type="select" className="form-control" name="cart.id">
                  <option value="" key="0" />
                  {carts
                    ? carts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/user-address" replace color="info">
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
  carts: storeState.cart.entities,
  userAddressEntity: storeState.userAddress.entity,
  loading: storeState.userAddress.loading,
  updating: storeState.userAddress.updating,
  updateSuccess: storeState.userAddress.updateSuccess,
});

const mapDispatchToProps = {
  getClientDetails,
  getCarts,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserAddressUpdate);
