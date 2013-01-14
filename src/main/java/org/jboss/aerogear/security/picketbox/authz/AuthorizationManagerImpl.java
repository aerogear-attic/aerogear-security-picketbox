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

package org.jboss.aerogear.security.picketbox.authz;

import org.jboss.aerogear.security.authz.AuthorizationManager;
import org.picketbox.cdi.PicketBoxIdentity;
import org.picketlink.authentication.AuthenticationException;

import javax.inject.Inject;

/**
 *  User authorization based on the submitted <i>token</i>
 */
public class AuthorizationManagerImpl implements AuthorizationManager {

    @Inject
    private PicketBoxIdentity identity;

    /**
     * Token validation against authorization provider
     * @param token the generated token for the current {@link org.jboss.aerogear.security.model.AeroGearUser} logged in.
     * @return true if the <i>token</i> is valid
     */
    @Override
    public boolean validate(String token) {

        boolean valid = false;

        if (token != null && !token.isEmpty()) {
            try {
                valid = identity.restoreSession(token);
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        }

        return valid;

    }
}
