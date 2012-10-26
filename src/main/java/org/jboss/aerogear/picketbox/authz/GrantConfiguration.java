/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
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

package org.jboss.aerogear.picketbox.authz;

import org.jboss.aerogear.security.authz.IdentityManagement;
import org.jboss.aerogear.security.model.AeroGearUser;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.Role;
import org.picketlink.idm.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GrantConfiguration implements IdentityManagement.GrantMethods {

    @Inject
    private IdentityManager identityManager;

    private List<Role> list;

    public GrantConfiguration roles(String[] roles) {
        list = new ArrayList<Role>();
        for (String role : roles) {
            Role newRole = identityManager.createRole(role);
            list.add(newRole);
        }
        return this;
    }

    /**
     * Passing null here because the api doesn' allows me to have user without a group
     *
     * @param user
     */
    @Override
    public void to(AeroGearUser user) {

        User picketLinkUser = identityManager.createUser(user.getId());
        user.setEmail(picketLinkUser.getEmail());
        user.setFirstName(picketLinkUser.getFirstName());
        user.setLastName(picketLinkUser.getLastName());

        identityManager.updatePassword(picketLinkUser, user.getPassword());

        for (Role role : list) {
            identityManager.grantRole(role, picketLinkUser, null);
        }

    }
}
