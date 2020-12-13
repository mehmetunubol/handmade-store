package com.unubol.demo.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.unubol.demo.store.web.rest.TestUtil;

public class UserAddressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAddress.class);
        UserAddress userAddress1 = new UserAddress();
        userAddress1.setId(1L);
        UserAddress userAddress2 = new UserAddress();
        userAddress2.setId(userAddress1.getId());
        assertThat(userAddress1).isEqualTo(userAddress2);
        userAddress2.setId(2L);
        assertThat(userAddress1).isNotEqualTo(userAddress2);
        userAddress1.setId(null);
        assertThat(userAddress1).isNotEqualTo(userAddress2);
    }
}
