import { IClientDetails } from 'app/shared/model/client-details.model';
import { ICart } from 'app/shared/model/cart.model';

export interface IUserAddress {
  id?: number;
  name?: string;
  type?: string;
  detail?: string;
  city?: string;
  country?: string;
  clientDetails?: IClientDetails;
  carts?: ICart[];
}

export const defaultValue: Readonly<IUserAddress> = {};
