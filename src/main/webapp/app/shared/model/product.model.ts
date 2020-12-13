import { IProductCategory } from 'app/shared/model/product-category.model';
import { IAttribute } from 'app/shared/model/attribute.model';

export interface IProduct {
  id?: number;
  name?: string;
  description?: string;
  price?: number;
  imageContentType?: string;
  image?: any;
  productCategories?: IProductCategory[];
  attributes?: IAttribute[];
}

export const defaultValue: Readonly<IProduct> = {};
