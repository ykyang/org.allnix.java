/*
 * Copyright 2018 Yi-Kun Yang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.allnix.oil.project.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.allnix.oil.model.OilObject;

@Entity
@Table(indexes = { //
    @Index(columnList = "projectId"), @Index(columnList = "name") })
public class Well extends OilObject {
    private String name;
    // private List<String> aliasList;

    /**
     * Project which this well belong to
     */
    @Column(length = 36)
    private String projectId;
    /**
     * Any other association
     */
    @ElementCollection
    @Column(length = 36)
    @JoinTable(//joinColumns = @JoinColumn(referencedColumnName = "id"),
        indexes = { @Index(columnList = "parentId") })
    private Set<String> parentId = new HashSet<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Well addParentId(String id) {
        parentId.add(id);
        return this;
    }
}
