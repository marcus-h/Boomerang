package boomerang.otf.benchmark;

import boomerang.BackwardQuery;
import boomerang.ForwardQuery;
import boomerang.Query;
import boomerang.scope.Statement;
import boomerang.scope.Method;
import boomerang.scope.CallGraph.Edge;

public class DataHarvester {

  public enum Phase {
    OBJECT_FLOW("object"), DATA_FLOW("data");

    private String id;

    Phase(String id) {
      this.id = id;
    }

    @Override
    public String toString() {
      return id;
    }
  }

  public enum Kind {
    EDGE("addEdge"), FORWARD_QUERY("forwardQuery"), BACKWARD_QUERY("backwardQuery");

    private String kind;

    Kind(String kind) {
      this.kind = kind;
    }

    @Override
    public String toString() {
      return kind;
    }
  }

  private static String DELIM = "#";

  private static Phase phase;

  public static void setPhase(Phase phase) {
    DataHarvester.phase = phase;
  }

  private static StringBuilder createStringBuilder(Kind kind) {
    StringBuilder builder = new StringBuilder(128);  // capacity is a guess
    builder.append(phase);
    builder.append(DELIM);
    builder.append(kind);
    builder.append(DELIM);
    return builder;
  }

  public static void logAddedEdge(String source, Edge edge) {
    StringBuilder builder = createStringBuilder(Kind.EDGE);
    builder.append(source);
    builder.append(DELIM);
    serializeStatement(builder, edge.src());
    builder.append(DELIM);
    serializeMethod(builder, edge.tgt());
    System.out.println(builder.toString());
  }

  private static void serializeStatement(StringBuilder builder, Statement stmt) {
    Method method = stmt.getMethod();
    serializeMethod(builder, method);
    builder.append(DELIM);
    builder.append(stmt.toString());
    builder.append(DELIM);
    builder.append(method.getControlFlowGraph().getStatements().indexOf(stmt));
  }

  private static void serializeMethod(StringBuilder builder, Method method) {
    builder.append(method.getDeclaringClass());
    builder.append(DELIM);
    builder.append(method.getSubSignature());
  }

  public static void logQuery(ForwardQuery query) {
    logQuery(Kind.FORWARD_QUERY, query);
  }

  public static void logQuery(BackwardQuery query) {
    logQuery(Kind.BACKWARD_QUERY, query);
  }

  private static void logQuery(Kind kind, Query query) {
    StringBuilder builder = createStringBuilder(kind);
    builder.append(query.toString().replaceAll("\n", "<NEW_LINE>"));
    System.out.println(builder.toString());
  }

}
