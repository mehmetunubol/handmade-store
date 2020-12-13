package com.unubol.demo.store.domain.enumeration;

/**
 * The OrderStatus enumeration.
 */
public enum OrderStatus {
    PENDING, WAITPAYMENT, WAITPAYCONFIRM, WAITSHIP, DECLINED, SHIPPING, COMPLETED, RETURNSHIPPING, RETURNED, CANCELLED
}
