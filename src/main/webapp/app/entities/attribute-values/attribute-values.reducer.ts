import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAttributeValues, defaultValue } from 'app/shared/model/attribute-values.model';

export const ACTION_TYPES = {
  FETCH_ATTRIBUTEVALUES_LIST: 'attributeValues/FETCH_ATTRIBUTEVALUES_LIST',
  FETCH_ATTRIBUTEVALUES: 'attributeValues/FETCH_ATTRIBUTEVALUES',
  CREATE_ATTRIBUTEVALUES: 'attributeValues/CREATE_ATTRIBUTEVALUES',
  UPDATE_ATTRIBUTEVALUES: 'attributeValues/UPDATE_ATTRIBUTEVALUES',
  DELETE_ATTRIBUTEVALUES: 'attributeValues/DELETE_ATTRIBUTEVALUES',
  RESET: 'attributeValues/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAttributeValues>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AttributeValuesState = Readonly<typeof initialState>;

// Reducer

export default (state: AttributeValuesState = initialState, action): AttributeValuesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ATTRIBUTEVALUES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ATTRIBUTEVALUES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ATTRIBUTEVALUES):
    case REQUEST(ACTION_TYPES.UPDATE_ATTRIBUTEVALUES):
    case REQUEST(ACTION_TYPES.DELETE_ATTRIBUTEVALUES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ATTRIBUTEVALUES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ATTRIBUTEVALUES):
    case FAILURE(ACTION_TYPES.CREATE_ATTRIBUTEVALUES):
    case FAILURE(ACTION_TYPES.UPDATE_ATTRIBUTEVALUES):
    case FAILURE(ACTION_TYPES.DELETE_ATTRIBUTEVALUES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTRIBUTEVALUES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTRIBUTEVALUES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ATTRIBUTEVALUES):
    case SUCCESS(ACTION_TYPES.UPDATE_ATTRIBUTEVALUES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ATTRIBUTEVALUES):
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

const apiUrl = 'api/attribute-values';

// Actions

export const getEntities: ICrudGetAllAction<IAttributeValues> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ATTRIBUTEVALUES_LIST,
    payload: axios.get<IAttributeValues>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAttributeValues> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ATTRIBUTEVALUES,
    payload: axios.get<IAttributeValues>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAttributeValues> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ATTRIBUTEVALUES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAttributeValues> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ATTRIBUTEVALUES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAttributeValues> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ATTRIBUTEVALUES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
