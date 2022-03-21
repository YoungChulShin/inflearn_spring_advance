package hello.proxy.jdkdynamic.code;

public class CImpl implements CInterface {

  @Override
  public int calculate(int a, int b) {
    return a + b;
  }
}
