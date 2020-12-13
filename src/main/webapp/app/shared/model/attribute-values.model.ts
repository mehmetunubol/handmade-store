import { IAttribute } from 'app/shared/model/attribute.model';

export interface IAttributeValues {
  id?: number;
  value?: string;
  price?: number;
  attribute?: IAttribute;
}

export const defaultValue: Readonly<IAttributeValues> = {};
