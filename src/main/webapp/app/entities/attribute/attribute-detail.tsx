import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './attribute.reducer';
import { IAttribute } from 'app/shared/model/attribute.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAttributeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AttributeDetail = (props: IAttributeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { attributeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Attribute [<b>{attributeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{attributeEntity.name}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>
            {attributeEntity.image ? (
              <div>
                {attributeEntity.imageContentType ? (
                  <a onClick={openFile(attributeEntity.imageContentType, attributeEntity.image)}>
                    <img src={`data:${attributeEntity.imageContentType};base64,${attributeEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {attributeEntity.imageContentType}, {byteSize(attributeEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/attribute" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attribute/${attributeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ attribute }: IRootState) => ({
  attributeEntity: attribute.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AttributeDetail);
