/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.aerogear.security.picketbox.auth;

import org.jboss.aerogear.security.auth.AuthenticationManager;
import org.jboss.aerogear.security.auth.CredentialFactory;
import org.jboss.aerogear.security.exception.AeroGearSecurityException;
import org.jboss.aerogear.security.model.AeroGearUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.picketlink.extensions.core.pbox.PicketBoxIdentity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationManagerTest {

    @Mock
    private AeroGearUser aeroGearUser;
    @Mock
    private PicketBoxIdentity picketBoxIdentity;
    @Mock
    private CredentialFactory credentialFactory;


    @InjectMocks
    private AuthenticationManager authenticationManager = new AuthenticationManagerImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(aeroGearUser.getUsername()).thenReturn("john");
        when(aeroGearUser.getPassword()).thenReturn("123");
    }

    @Test(expected = AeroGearSecurityException.class)
    public void testInvalidLogin() throws Exception {
        when(picketBoxIdentity.isLoggedIn()).thenReturn(false);
        authenticationManager.login(aeroGearUser);
    }

    @Test
    public void testLogout() throws Exception {
        when(picketBoxIdentity.isLoggedIn()).thenReturn(true);
        authenticationManager.logout();
        verify(picketBoxIdentity).logout();
    }
}
