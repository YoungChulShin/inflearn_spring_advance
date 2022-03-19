package hello.proxy.pureproxy.decorator.code;

public abstract class Decorator implements Component {

  protected Component realComponent;

  public Decorator(Component realComponent) {
    this.realComponent = realComponent;
  }
}
