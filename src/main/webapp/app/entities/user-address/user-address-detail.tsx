import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-address.reducer';
import { IUserAddress } from 'app/shared/model/user-address.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserAddressDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserAddressDetail = (props: IUserAddressDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userAddressEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          UserAddress [<b>{userAddressEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{userAddressEntity.name}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{userAddressEntity.type}</dd>
          <dt>
            <span id="detail">Detail</span>
          </dt>
          <dd>{userAddressEntity.detail}</dd>
          <dt>
            <span id="city">City</span>
          </dt>
          <dd>{userAddressEntity.city}</dd>
          <dt>
            <span id="country">Country</span>
          </dt>
          <dd>{userAddressEntity.country}</dd>
          <dt>Client Details</dt>
          <dd>{userAddressEntity.clientDetails ? userAddressEntity.clientDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-address" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-address/${userAddressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userAddress }: IRootState) => ({
  userAddressEntity: userAddress.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserAddressDetail);
