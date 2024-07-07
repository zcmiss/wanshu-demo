package com.wanshu.flowable.modeler.constants;

import java.util.HashSet;
import java.util.Set;

public class FlowableConstants {

    public static Set<String> FLOW_ABLE_MODELER_ROLES = new HashSet<>();

    static {
        FLOW_ABLE_MODELER_ROLES.add("access-idm");
        FLOW_ABLE_MODELER_ROLES.add("access-rest-api");
        FLOW_ABLE_MODELER_ROLES.add("access-task");
        FLOW_ABLE_MODELER_ROLES.add("access-modeler");
        FLOW_ABLE_MODELER_ROLES.add("access-admin");
    }
}
