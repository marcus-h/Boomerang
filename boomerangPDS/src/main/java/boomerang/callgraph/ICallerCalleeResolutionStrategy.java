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
import boomerang.scope.Method;
import boomerang.scope.Statement;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ICallerCalleeResolutionStrategy {

  interface Factory {
    ICallerCalleeResolutionStrategy newInstance(WeightedBoomerang solver, CallGraph cg);
  }

  public interface OnCallerCalleeFoundCallback {
    void accept(String source, Statement stmt, Method method);
  }

  boolean computeFallback(
      OnCallerCalleeFoundCallback onCallerCalleeFoundCallback,
      Consumer<Statement> onNoCalleeFoundCallback);

  void resolveCallersForCalleeFallback(
      Method callee, OnCallerCalleeFoundCallback onCallerCalleeFoundCallback);

  void resolveSpecialInvoke(
      Statement stmt, OnCallerCalleeFoundCallback onCallerCalleeFoundCallback);

  void resolveInstanceInvoke(
      Statement stmt, OnCallerCalleeFoundCallback onCallerCalleeFoundCallback);

  void resolveStaticInvoke(
      Statement stmt, OnCallerCalleeFoundCallback onCallerCalleeFoundCallback);
}
