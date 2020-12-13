import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AttributeValues from './attribute-values';
import AttributeValuesDetail from './attribute-values-detail';
import AttributeValuesUpdate from './attribute-values-update';
import AttributeValuesDeleteDialog from './attribute-values-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AttributeValuesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AttributeValuesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AttributeValuesDetail} />
      <ErrorBoundaryRoute path={match.url} component={AttributeValues} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AttributeValuesDeleteDialog} />
  </>
);

export default Routes;
