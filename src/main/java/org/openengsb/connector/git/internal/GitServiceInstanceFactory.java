/**
 * Licensed to the Austrian Association for Software Tool Integration (AASTI)
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. The AASTI licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.connector.git.internal;

import java.util.HashMap;
import java.util.Map;

import org.openengsb.core.api.ServiceInstanceFactory;
import org.openengsb.core.api.descriptor.ServiceDescriptor;
import org.openengsb.core.api.validation.MultipleAttributeValidationResult;
import org.openengsb.core.api.validation.MultipleAttributeValidationResultImpl;
import org.openengsb.domain.scm.ScmDomain;

public class GitServiceInstanceFactory implements ServiceInstanceFactory<ScmDomain, GitServiceImpl> {

    public GitServiceInstanceFactory() {
    }

    @Override
    public void updateServiceInstance(GitServiceImpl instance, Map<String, String> attributes) {
        if (attributes.containsKey("repository")) {
            instance.setRemoteLocation(attributes.get("repository"));
        }
        if (attributes.containsKey("workspace")) {
            instance.setLocalWorkspace(attributes.get("workspace"));
        }
        if (attributes.containsKey("branch")) {
            instance.setWatchBranch(attributes.get("branch"));
        }
    }

    @Override
    public GitServiceImpl createServiceInstance(String id, Map<String, String> attributes) {
        GitServiceImpl service = new GitServiceImpl(id);
        updateServiceInstance(service, attributes);
        return service;
    }

    @Override
    public ServiceDescriptor getDescriptor(ServiceDescriptor.Builder builder) {
        builder.name("service.name").description("service.description");
        builder.attribute(builder.newAttribute().id("repository").name("service.repository.name")
            .description("service.repository.description").build());
        builder.attribute(builder.newAttribute().id("workspace").name("service.workspace.name")
            .description("service.workspace.description").build());
        builder.attribute(builder.newAttribute().id("branch").name("service.branch.name")
            .description("service.branch.description").build());
        return builder.build();
    }

    @Override
    public MultipleAttributeValidationResult updateValidation(
            GitServiceImpl instance, Map<String, String> attributes) {
        updateServiceInstance(instance, attributes);
        return new MultipleAttributeValidationResultImpl(true, new HashMap<String, String>());
    }

    @Override
    public MultipleAttributeValidationResult createValidation(String id,
            Map<String, String> attributes) {
        return new MultipleAttributeValidationResultImpl(true, new HashMap<String, String>());
    }
}
