import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './order-items.reducer';
import { IOrderItems } from 'app/shared/model/order-items.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderItemsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const OrderItems = (props: IOrderItemsProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { orderItemsList, match, loading } = props;
  return (
    <div>
      <h2 id="order-items-heading">
        Order Items
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Order Items
        </Link>
      </h2>
      <div className="table-responsive">
        {orderItemsList && orderItemsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Product</th>
                <th>Cart</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orderItemsList.map((orderItems, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${orderItems.id}`} color="link" size="sm">
                      {orderItems.id}
                    </Button>
                  </td>
                  <td>{orderItems.quantity}</td>
                  <td>{orderItems.price}</td>
                  <td>{orderItems.product ? <Link to={`product/${orderItems.product.id}`}>{orderItems.product.name}</Link> : ''}</td>
                  <td>{orderItems.cart ? <Link to={`cart/${orderItems.cart.id}`}>{orderItems.cart.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${orderItems.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderItems.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderItems.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Order Items found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ orderItems }: IRootState) => ({
  orderItemsList: orderItems.entities,
  loading: orderItems.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderItems);
