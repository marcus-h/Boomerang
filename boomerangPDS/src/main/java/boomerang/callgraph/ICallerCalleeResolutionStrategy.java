/**
 * ***************************************************************************** 
 * Copyright (c) 2018 Fraunhofer IEM, Paderborn, Germany
 * <p>
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * <p>
 * SPDX-License-Identifier: EPL-2.0
 * <p>
 * Contributors:
 *   Johannes Spaeth - initial API and implementation
 * *****************************************************************************
 */
package boomerang.callgraph;

import boomerang.WeightedBoomerang;
import boomerang.scope.CallGraph;
import boomerang.scope.Statement;

public interface ICallerCalleeResolutionStrategy {

  interface Factory {
    ICallerCalleeResolutionStrategy newInstance(WeightedBoomerang solver, CallGraph cg);
  }

  void computeFallback(ObservableDynamicICFG observableDynamicICFG);

  CallGraph getPrecomputedCallGraph();

  void setObservableDynamicICFG(ObservableDynamicICFG observableDynamicICFG);

  void resolveSpecialInvoke(Statement stmt);

  void resolveInstanceInvoke(Statement stmt);

  void resolveStaticInvoke(Statement stmt);
}
