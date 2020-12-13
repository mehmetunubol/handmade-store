import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAttribute } from 'app/shared/model/attribute.model';
import { getEntities as getAttributes } from 'app/entities/attribute/attribute.reducer';
import { getEntity, updateEntity, createEntity, reset } from './attribute-values.reducer';
import { IAttributeValues } from 'app/shared/model/attribute-values.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAttributeValuesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AttributeValuesUpdate = (props: IAttributeValuesUpdateProps) => {
  const [attributeId, setAttributeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { attributeValuesEntity, attributes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/attribute-values' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAttributes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...attributeValuesEntity,
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
          <h2 id="storeApp.attributeValues.home.createOrEditLabel">Create or edit a AttributeValues</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : attributeValuesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="attribute-values-id">ID</Label>
                  <AvInput id="attribute-values-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valueLabel" for="attribute-values-value">
                  Value
                </Label>
                <AvField
                  id="attribute-values-value"
                  type="text"
                  name="value"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="attribute-values-price">
                  Price
                </Label>
                <AvField
                  id="attribute-values-price"
                  type="text"
                  name="price"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    min: { value: 0, errorMessage: 'This field should be at least 0.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="attribute-values-attribute">Attribute</Label>
                <AvInput id="attribute-values-attribute" type="select" className="form-control" name="attribute.id">
                  <option value="" key="0" />
                  {attributes
                    ? attributes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/attribute-values" replace color="info">
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
  attributes: storeState.attribute.entities,
  attributeValuesEntity: storeState.attributeValues.entity,
  loading: storeState.attributeValues.loading,
  updating: storeState.attributeValues.updating,
  updateSuccess: storeState.attributeValues.updateSuccess,
});

const mapDispatchToProps = {
  getAttributes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AttributeValuesUpdate);
