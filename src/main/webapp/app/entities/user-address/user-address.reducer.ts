import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserAddress, defaultValue } from 'app/shared/model/user-address.model';

export const ACTION_TYPES = {
  FETCH_USERADDRESS_LIST: 'userAddress/FETCH_USERADDRESS_LIST',
  FETCH_USERADDRESS: 'userAddress/FETCH_USERADDRESS',
  CREATE_USERADDRESS: 'userAddress/CREATE_USERADDRESS',
  UPDATE_USERADDRESS: 'userAddress/UPDATE_USERADDRESS',
  DELETE_USERADDRESS: 'userAddress/DELETE_USERADDRESS',
  RESET: 'userAddress/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserAddress>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type UserAddressState = Readonly<typeof initialState>;

// Reducer

export default (state: UserAddressState = initialState, action): UserAddressState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERADDRESS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERADDRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USERADDRESS):
    case REQUEST(ACTION_TYPES.UPDATE_USERADDRESS):
    case REQUEST(ACTION_TYPES.DELETE_USERADDRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USERADDRESS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERADDRESS):
    case FAILURE(ACTION_TYPES.CREATE_USERADDRESS):
    case FAILURE(ACTION_TYPES.UPDATE_USERADDRESS):
    case FAILURE(ACTION_TYPES.DELETE_USERADDRESS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERADDRESS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERADDRESS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERADDRESS):
    case SUCCESS(ACTION_TYPES.UPDATE_USERADDRESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERADDRESS):
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

const apiUrl = 'api/user-addresses';

// Actions

export const getEntities: ICrudGetAllAction<IUserAddress> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USERADDRESS_LIST,
    payload: axios.get<IUserAddress>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IUserAddress> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERADDRESS,
    payload: axios.get<IUserAddress>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserAddress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERADDRESS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserAddress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERADDRESS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserAddress> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERADDRESS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
