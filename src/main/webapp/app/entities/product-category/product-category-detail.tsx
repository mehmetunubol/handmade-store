import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product-category.reducer';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductCategoryDetail = (props: IProductCategoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productCategoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ProductCategory [<b>{productCategoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{productCategoryEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{productCategoryEntity.description}</dd>
          <dt>
            <span id="parent">Parent</span>
          </dt>
          <dd>{productCategoryEntity.parent}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>
            {productCategoryEntity.image ? (
              <div>
                {productCategoryEntity.imageContentType ? (
                  <a onClick={openFile(productCategoryEntity.imageContentType, productCategoryEntity.image)}>
                    <img
                      src={`data:${productCategoryEntity.imageContentType};base64,${productCategoryEntity.image}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {productCategoryEntity.imageContentType}, {byteSize(productCategoryEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/product-category" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-category/${productCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ productCategory }: IRootState) => ({
  productCategoryEntity: productCategory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductCategoryDetail);
