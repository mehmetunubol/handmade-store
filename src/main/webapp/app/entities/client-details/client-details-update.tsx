import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICart } from 'app/shared/model/cart.model';
import { getEntities as getCarts } from 'app/entities/cart/cart.reducer';
import { getEntity, updateEntity, createEntity, reset } from './client-details.reducer';
import { IClientDetails } from 'app/shared/model/client-details.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientDetailsUpdate = (props: IClientDetailsUpdateProps) => {
  const [idscart, setIdscart] = useState([]);
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { clientDetailsEntity, users, carts, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/client-details' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
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
        ...clientDetailsEntity,
        ...values,
        carts: mapIdList(values.carts),
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
          <h2 id="storeApp.clientDetails.home.createOrEditLabel">Create or edit a ClientDetails</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : clientDetailsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="client-details-id">ID</Label>
                  <AvInput id="client-details-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="genderLabel" for="client-details-gender">
                  Gender
                </Label>
                <AvInput
                  id="client-details-gender"
                  type="select"
                  className="form-control"
                  name="gender"
                  value={(!isNew && clientDetailsEntity.gender) || 'MALE'}
                >
                  <option value="MALE">MALE</option>
                  <option value="FEMALE">FEMALE</option>
                  <option value="OTHER">OTHER</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="phoneLabel" for="client-details-phone">
                  Phone
                </Label>
                <AvField
                  id="client-details-phone"
                  type="text"
                  name="phone"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="cityLabel" for="client-details-city">
                  City
                </Label>
                <AvField
                  id="client-details-city"
                  type="text"
                  name="city"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="countryLabel" for="client-details-country">
                  Country
                </Label>
                <AvField
                  id="client-details-country"
                  type="text"
                  name="country"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="client-details-user">User</Label>
                <AvInput
                  id="client-details-user"
                  type="select"
                  className="form-control"
                  name="user.id"
                  value={isNew ? users[0] && users[0].id : clientDetailsEntity.user?.id}
                  required
                >
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="client-details-cart">Cart</Label>
                <AvInput
                  id="client-details-cart"
                  type="select"
                  multiple
                  className="form-control"
                  name="carts"
                  value={clientDetailsEntity.carts && clientDetailsEntity.carts.map(e => e.id)}
                >
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
              <Button tag={Link} id="cancel-save" to="/client-details" replace color="info">
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
  users: storeState.userManagement.users,
  carts: storeState.cart.entities,
  clientDetailsEntity: storeState.clientDetails.entity,
  loading: storeState.clientDetails.loading,
  updating: storeState.clientDetails.updating,
  updateSuccess: storeState.clientDetails.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getCarts,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientDetailsUpdate);
