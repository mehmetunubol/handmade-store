import { IUser } from 'app/shared/model/user.model';
import { ICart } from 'app/shared/model/cart.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IClientDetails {
  id?: number;
  gender?: Gender;
  phone?: string;
  city?: string;
  country?: string;
  user?: IUser;
  carts?: ICart[];
}

export const defaultValue: Readonly<IClientDetails> = {};
