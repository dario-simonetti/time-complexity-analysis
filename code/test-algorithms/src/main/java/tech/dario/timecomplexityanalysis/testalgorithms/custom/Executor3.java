package tech.dario.timecomplexityanalysis.testalgorithms.custom;

public class Executor3 {
  private Quadratic quadratic;
  private Logarithmic logarithmic;
  private Linearithmic linearithmic;
  private Linear linear;
  private Exponential exponential;
  private Cubic cubic;
  private Constant constant;

  public Executor3() {
    constant = new Constant();
    cubic = new Cubic();
    exponential = new Exponential();
    linear = new Linear();
    linearithmic = new Linearithmic();
    logarithmic = new Logarithmic();
    quadratic = new Quadratic();
  }

  public void execute(long n) {
    cubic.average(n / 30);
  }
}
