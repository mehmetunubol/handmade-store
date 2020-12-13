import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClientDetails from './client-details';
import UserAddress from './user-address';
import Cart from './cart';
import OrderItems from './order-items';
import Product from './product';
import Attribute from './attribute';
import AttributeValues from './attribute-values';
import ProductCategory from './product-category';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}client-details`} component={ClientDetails} />
      <ErrorBoundaryRoute path={`${match.url}user-address`} component={UserAddress} />
      <ErrorBoundaryRoute path={`${match.url}cart`} component={Cart} />
      <ErrorBoundaryRoute path={`${match.url}order-items`} component={OrderItems} />
      <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}attribute`} component={Attribute} />
      <ErrorBoundaryRoute path={`${match.url}attribute-values`} component={AttributeValues} />
      <ErrorBoundaryRoute path={`${match.url}product-category`} component={ProductCategory} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
