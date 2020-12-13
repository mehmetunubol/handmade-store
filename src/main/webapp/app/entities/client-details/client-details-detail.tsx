import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client-details.reducer';
import { IClientDetails } from 'app/shared/model/client-details.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientDetailsDetail = (props: IClientDetailsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { clientDetailsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ClientDetails [<b>{clientDetailsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{clientDetailsEntity.gender}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{clientDetailsEntity.phone}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{clientDetailsEntity.city}</dd>
          <dt>
            <span id="country">Country</span>
          </dt>
          <dd>{clientDetailsEntity.country}</dd>
          <dt>User</dt>
          <dd>{clientDetailsEntity.user ? clientDetailsEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/client-details" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client-details/${clientDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ clientDetails }: IRootState) => ({
  clientDetailsEntity: clientDetails.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientDetailsDetail);
