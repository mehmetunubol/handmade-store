import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrderItems, defaultValue } from 'app/shared/model/order-items.model';

export const ACTION_TYPES = {
  FETCH_ORDERITEMS_LIST: 'orderItems/FETCH_ORDERITEMS_LIST',
  FETCH_ORDERITEMS: 'orderItems/FETCH_ORDERITEMS',
  CREATE_ORDERITEMS: 'orderItems/CREATE_ORDERITEMS',
  UPDATE_ORDERITEMS: 'orderItems/UPDATE_ORDERITEMS',
  DELETE_ORDERITEMS: 'orderItems/DELETE_ORDERITEMS',
  RESET: 'orderItems/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderItems>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type OrderItemsState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderItemsState = initialState, action): OrderItemsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERITEMS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERITEMS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERITEMS):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERITEMS):
    case REQUEST(ACTION_TYPES.DELETE_ORDERITEMS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERITEMS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERITEMS):
    case FAILURE(ACTION_TYPES.CREATE_ORDERITEMS):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERITEMS):
    case FAILURE(ACTION_TYPES.DELETE_ORDERITEMS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERITEMS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERITEMS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERITEMS):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERITEMS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERITEMS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/order-items';

// Actions

export const getEntities: ICrudGetAllAction<IOrderItems> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORDERITEMS_LIST,
  payload: axios.get<IOrderItems>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IOrderItems> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERITEMS,
    payload: axios.get<IOrderItems>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOrderItems> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERITEMS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrderItems> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERITEMS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderItems> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERITEMS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
