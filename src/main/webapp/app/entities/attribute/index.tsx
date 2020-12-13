import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Attribute from './attribute';
import AttributeDetail from './attribute-detail';
import AttributeUpdate from './attribute-update';
import AttributeDeleteDialog from './attribute-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AttributeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AttributeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AttributeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Attribute} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AttributeDeleteDialog} />
  </>
);

export default Routes;
