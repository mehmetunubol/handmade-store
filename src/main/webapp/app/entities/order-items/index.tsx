import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderItems from './order-items';
import OrderItemsDetail from './order-items-detail';
import OrderItemsUpdate from './order-items-update';
import OrderItemsDeleteDialog from './order-items-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderItemsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderItemsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderItemsDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderItems} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrderItemsDeleteDialog} />
  </>
);

export default Routes;
