/*
 * Copyright (c) 2014, Cloudera, Inc. All Rights Reserved.
 *
 * Cloudera, Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */

package com.cloudera.oryx.ml.serving.als;

import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.GenericType;

import com.cloudera.oryx.lambda.KeyMessage;
import com.cloudera.oryx.lambda.serving.AbstractServingTest;
import com.cloudera.oryx.lambda.serving.ServingModelManager;
import com.cloudera.oryx.ml.serving.IDValue;
import com.cloudera.oryx.ml.serving.als.model.ALSServingModel;
import com.cloudera.oryx.ml.serving.als.model.TestALSModelFactory;

public abstract class AbstractALSServingTest extends AbstractServingTest {

  protected static final GenericType<List<IDValue>> LIST_ID_VALUE_TYPE =
      new GenericType<List<IDValue>>() {};

  @Override
  protected final Class<? extends ServletContextListener> getInitListenerClass() {
    return MockManagerInitListener.class;
  }

  public static final class MockManagerInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
      sce.getServletContext().setAttribute(
          AbstractALSResource.MODEL_MANAGER_KEY,
          new MockServingModelManager());
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
      // do nothing
    }
  }

  private static final class MockServingModelManager implements ServingModelManager<String> {
    @Override
    public void consume(Iterator<KeyMessage<String, String>> updateIterator) {
      throw new UnsupportedOperationException();
    }
    @Override
    public ALSServingModel getModel() {
      return TestALSModelFactory.buildTestModel();
    }
    @Override
    public void close() {
      // do nothing
    }
  }

}