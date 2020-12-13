import { Moment } from 'moment';
import { IUserAddress } from 'app/shared/model/user-address.model';
import { IOrderItems } from 'app/shared/model/order-items.model';
import { IClientDetails } from 'app/shared/model/client-details.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';

export interface ICart {
  id?: number;
  notes?: string;
  placedDate?: string;
  status?: OrderStatus;
  totalPrice?: number;
  paymentMethod?: PaymentMethod;
  addresses?: IUserAddress[];
  orders?: IOrderItems[];
  clientDetails?: IClientDetails;
}

export const defaultValue: Readonly<ICart> = {};
