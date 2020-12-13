import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserAddress from './user-address';
import UserAddressDetail from './user-address-detail';
import UserAddressUpdate from './user-address-update';
import UserAddressDeleteDialog from './user-address-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserAddressDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserAddress} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserAddressDeleteDialog} />
  </>
);

export default Routes;
