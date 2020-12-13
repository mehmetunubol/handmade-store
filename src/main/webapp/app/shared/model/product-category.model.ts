import { IProduct } from 'app/shared/model/product.model';

export interface IProductCategory {
  id?: number;
  name?: string;
  description?: string;
  imageContentType?: string;
  image?: any;
  parents?: IProductCategory[];
  productCategory?: IProductCategory;
  product?: IProduct;
}

export const defaultValue: Readonly<IProductCategory> = {};
