import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClientDetails, defaultValue } from 'app/shared/model/client-details.model';

export const ACTION_TYPES = {
  FETCH_CLIENTDETAILS_LIST: 'clientDetails/FETCH_CLIENTDETAILS_LIST',
  FETCH_CLIENTDETAILS: 'clientDetails/FETCH_CLIENTDETAILS',
  CREATE_CLIENTDETAILS: 'clientDetails/CREATE_CLIENTDETAILS',
  UPDATE_CLIENTDETAILS: 'clientDetails/UPDATE_CLIENTDETAILS',
  DELETE_CLIENTDETAILS: 'clientDetails/DELETE_CLIENTDETAILS',
  RESET: 'clientDetails/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClientDetails>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ClientDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: ClientDetailsState = initialState, action): ClientDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLIENTDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLIENTDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CLIENTDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_CLIENTDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_CLIENTDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CLIENTDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLIENTDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_CLIENTDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_CLIENTDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_CLIENTDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTDETAILS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLIENTDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_CLIENTDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLIENTDETAILS):
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

const apiUrl = 'api/client-details';

// Actions

export const getEntities: ICrudGetAllAction<IClientDetails> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTDETAILS_LIST,
    payload: axios.get<IClientDetails>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IClientDetails> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTDETAILS,
    payload: axios.get<IClientDetails>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IClientDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLIENTDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClientDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLIENTDETAILS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClientDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLIENTDETAILS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
