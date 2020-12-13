import { IAttributeValues } from 'app/shared/model/attribute-values.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IAttribute {
  id?: number;
  name?: string;
  imageContentType?: string;
  image?: any;
  attributeValues?: IAttributeValues[];
  products?: IProduct[];
}

export const defaultValue: Readonly<IAttribute> = {};
