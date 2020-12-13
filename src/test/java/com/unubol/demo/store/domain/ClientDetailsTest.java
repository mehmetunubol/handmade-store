package com.unubol.demo.store.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.unubol.demo.store.web.rest.TestUtil;

public class ClientDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDetails.class);
        ClientDetails clientDetails1 = new ClientDetails();
        clientDetails1.setId(1L);
        ClientDetails clientDetails2 = new ClientDetails();
        clientDetails2.setId(clientDetails1.getId());
        assertThat(clientDetails1).isEqualTo(clientDetails2);
        clientDetails2.setId(2L);
        assertThat(clientDetails1).isNotEqualTo(clientDetails2);
        clientDetails1.setId(null);
        assertThat(clientDetails1).isNotEqualTo(clientDetails2);
    }
}
