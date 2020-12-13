import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAttribute, defaultValue } from 'app/shared/model/attribute.model';

export const ACTION_TYPES = {
  FETCH_ATTRIBUTE_LIST: 'attribute/FETCH_ATTRIBUTE_LIST',
  FETCH_ATTRIBUTE: 'attribute/FETCH_ATTRIBUTE',
  CREATE_ATTRIBUTE: 'attribute/CREATE_ATTRIBUTE',
  UPDATE_ATTRIBUTE: 'attribute/UPDATE_ATTRIBUTE',
  DELETE_ATTRIBUTE: 'attribute/DELETE_ATTRIBUTE',
  SET_BLOB: 'attribute/SET_BLOB',
  RESET: 'attribute/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAttribute>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AttributeState = Readonly<typeof initialState>;

// Reducer

export default (state: AttributeState = initialState, action): AttributeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ATTRIBUTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ATTRIBUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ATTRIBUTE):
    case REQUEST(ACTION_TYPES.UPDATE_ATTRIBUTE):
    case REQUEST(ACTION_TYPES.DELETE_ATTRIBUTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ATTRIBUTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ATTRIBUTE):
    case FAILURE(ACTION_TYPES.CREATE_ATTRIBUTE):
    case FAILURE(ACTION_TYPES.UPDATE_ATTRIBUTE):
    case FAILURE(ACTION_TYPES.DELETE_ATTRIBUTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTRIBUTE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ATTRIBUTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ATTRIBUTE):
    case SUCCESS(ACTION_TYPES.UPDATE_ATTRIBUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ATTRIBUTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/attributes';

// Actions

export const getEntities: ICrudGetAllAction<IAttribute> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ATTRIBUTE_LIST,
    payload: axios.get<IAttribute>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAttribute> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ATTRIBUTE,
    payload: axios.get<IAttribute>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAttribute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ATTRIBUTE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAttribute> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ATTRIBUTE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAttribute> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ATTRIBUTE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
