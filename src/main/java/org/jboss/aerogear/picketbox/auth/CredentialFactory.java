package org.jboss.aerogear.picketbox.auth;

import org.jboss.aerogear.picketbox.model.AeroGearUser;
import org.picketlink.credential.Credential;

public interface CredentialFactory extends Credential {

    void setCredential(AeroGearUser user);

}