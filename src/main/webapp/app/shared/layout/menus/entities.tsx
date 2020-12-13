import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/client-details">
      Client Details
    </MenuItem>
    <MenuItem icon="asterisk" to="/user-address">
      User Address
    </MenuItem>
    <MenuItem icon="asterisk" to="/cart">
      Cart
    </MenuItem>
    <MenuItem icon="asterisk" to="/order-items">
      Order Items
    </MenuItem>
    <MenuItem icon="asterisk" to="/product">
      Product
    </MenuItem>
    <MenuItem icon="asterisk" to="/attribute">
      Attribute
    </MenuItem>
    <MenuItem icon="asterisk" to="/attribute-values">
      Attribute Values
    </MenuItem>
    <MenuItem icon="asterisk" to="/product-category">
      Product Category
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
