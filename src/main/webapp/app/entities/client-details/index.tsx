import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientDetails from './client-details';
import ClientDetailsDetail from './client-details-detail';
import ClientDetailsUpdate from './client-details-update';
import ClientDetailsDeleteDialog from './client-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClientDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClientDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClientDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClientDetails} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClientDetailsDeleteDialog} />
  </>
);

export default Routes;
