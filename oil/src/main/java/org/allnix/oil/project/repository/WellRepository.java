/*
 * Copyright 2018 Yi-Kun Yang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.allnix.oil.project.repository;

import java.util.List;
import java.util.Optional;

import org.allnix.oil.project.model.Well;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 *
 * @author Yi-Kun Yang ykyang@gmail.com
 */
@Repository
public interface WellRepository extends JpaRepository<Well, String> {
    Optional<Well> findFirstByName(String name);
    List<Well> findByName(String name);
    List<Well> findByProjectId(String projectId);
    List<Well> findByParentId(String parentId);
}