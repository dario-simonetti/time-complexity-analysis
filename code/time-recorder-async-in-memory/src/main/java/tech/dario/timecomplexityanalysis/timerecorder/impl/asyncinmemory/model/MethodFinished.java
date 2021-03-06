package tech.dario.timecomplexityanalysis.timerecorder.impl.asyncinmemory.model;

public final class MethodFinished implements MethodAction {
  private final String methodLongName;
  private final long nanoTime;

  public MethodFinished(String methodLongName, long nanoTime) {
    this.methodLongName = methodLongName;
    this.nanoTime = nanoTime;
  }

  @Override
  public final String getMethodLongName() {
    return methodLongName;
  }

  @Override
  public final long getNanoTime() {
    return nanoTime;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MethodFinished that = (MethodFinished) o;

    if (nanoTime != that.nanoTime) return false;
    return methodLongName != null ? methodLongName.equals(that.methodLongName) : that.methodLongName == null;
  }

  @Override
  public final int hashCode() {
    int result = methodLongName != null ? methodLongName.hashCode() : 0;
    result = 31 * result + (int) (nanoTime ^ (nanoTime >>> 32));
    return result;
  }

  @Override
  public final String toString() {
    return "MethodFinished{" +
        "methodLongName='" + methodLongName + '\'' +
        ", nanoTime=" + nanoTime +
        '}';
  }
}
