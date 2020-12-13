import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './attribute-values.reducer';
import { IAttributeValues } from 'app/shared/model/attribute-values.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAttributeValuesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AttributeValuesDetail = (props: IAttributeValuesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { attributeValuesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          AttributeValues [<b>{attributeValuesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="value">Value</span>
          </dt>
          <dd>{attributeValuesEntity.value}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{attributeValuesEntity.price}</dd>
          <dt>Attribute</dt>
          <dd>{attributeValuesEntity.attribute ? attributeValuesEntity.attribute.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/attribute-values" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attribute-values/${attributeValuesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ attributeValues }: IRootState) => ({
  attributeValuesEntity: attributeValues.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AttributeValuesDetail);
