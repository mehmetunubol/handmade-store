import { IProduct } from 'app/shared/model/product.model';
import { ICart } from 'app/shared/model/cart.model';

export interface IOrderItems {
  id?: number;
  quantity?: number;
  price?: number;
  product?: IProduct;
  cart?: ICart;
}

export const defaultValue: Readonly<IOrderItems> = {};
