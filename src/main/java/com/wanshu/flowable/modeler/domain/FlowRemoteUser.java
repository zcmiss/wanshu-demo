package com.wanshu.flowable.modeler.domain;

import org.flowable.idm.api.User;
import org.flowable.ui.common.model.RemoteGroup;
import org.flowable.ui.common.model.RemoteUser;

import java.util.ArrayList;
import java.util.List;

public class FlowRemoteUser implements User {

    protected String id;
    protected String firstName;
    protected String lastName;
    protected String displayName;
    protected String email;
    protected String password;
    protected String fullName;
    protected String tenantId;
    protected List<RemoteGroup> groups = new ArrayList();
    protected List<String> privileges = new ArrayList();

    public FlowRemoteUser() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<RemoteGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(List<RemoteGroup> groups) {
        this.groups = groups;
    }

    public List<String> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPictureSet() {
        return false;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
