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
package org.allnix.oil.project;

import org.allnix.oil.TestSpringApplication;
import org.allnix.oil.project.model.Well;
import org.allnix.oil.project.repository.ProjectRepository;
import org.allnix.oil.project.repository.WellRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("unit-test")
// @ExtendWith(MockitoExtension.class)
//@SpringBootApplication(exclude = { MongoAutoConfiguration.class,
//MongoDataAutoConfiguration.class })
//@SpringBootTest(webEnvironment = WebEnvironment.NONE) //classes = TestSpringApplication.class,
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UnitTestConfig.class })
@TestInstance(Lifecycle.PER_CLASS)
@Tag("unit")
public class ProjectServiceUnitTest {
    static final private Logger logger = LoggerFactory
        .getLogger(ProjectServiceUnitTest.class);
    @Autowired
    private DefaultProjectService ps;

    @Autowired
    private WellRepository wellDao;
    @Autowired
    private ProjectRepository projectDao;

    @Test
    void testSave() {
        assertNotNull(wellDao);
        logger.info("WellDao: {}", wellDao.getClass().getName());
        Well well = mock(Well.class);
        ps.save(well);
        verify(wellDao).save(well);
    }
}
