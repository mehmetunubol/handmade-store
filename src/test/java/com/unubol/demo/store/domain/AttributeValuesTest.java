package com.unubol.demo.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.unubol.demo.store.web.rest.TestUtil;

public class AttributeValuesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeValues.class);
        AttributeValues attributeValues1 = new AttributeValues();
        attributeValues1.setId(1L);
        AttributeValues attributeValues2 = new AttributeValues();
        attributeValues2.setId(attributeValues1.getId());
        assertThat(attributeValues1).isEqualTo(attributeValues2);
        attributeValues2.setId(2L);
        assertThat(attributeValues1).isNotEqualTo(attributeValues2);
        attributeValues1.setId(null);
        assertThat(attributeValues1).isNotEqualTo(attributeValues2);
    }
}
