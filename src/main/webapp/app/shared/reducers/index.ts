import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import clientDetails, {
  ClientDetailsState
} from 'app/entities/client-details/client-details.reducer';
// prettier-ignore
import userAddress, {
  UserAddressState
} from 'app/entities/user-address/user-address.reducer';
// prettier-ignore
import cart, {
  CartState
} from 'app/entities/cart/cart.reducer';
// prettier-ignore
import orderItems, {
  OrderItemsState
} from 'app/entities/order-items/order-items.reducer';
// prettier-ignore
import product, {
  ProductState
} from 'app/entities/product/product.reducer';
// prettier-ignore
import attribute, {
  AttributeState
} from 'app/entities/attribute/attribute.reducer';
// prettier-ignore
import attributeValues, {
  AttributeValuesState
} from 'app/entities/attribute-values/attribute-values.reducer';
// prettier-ignore
import productCategory, {
  ProductCategoryState
} from 'app/entities/product-category/product-category.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly clientDetails: ClientDetailsState;
  readonly userAddress: UserAddressState;
  readonly cart: CartState;
  readonly orderItems: OrderItemsState;
  readonly product: ProductState;
  readonly attribute: AttributeState;
  readonly attributeValues: AttributeValuesState;
  readonly productCategory: ProductCategoryState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  clientDetails,
  userAddress,
  cart,
  orderItems,
  product,
  attribute,
  attributeValues,
  productCategory,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
