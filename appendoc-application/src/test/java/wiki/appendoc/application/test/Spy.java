package wiki.appendoc.application.test;

public final class Spy {

    private Spy() {
    }

    public static Spy getNewSpy() {
        return new Spy();
    }

    private long callCount = 0;

    public boolean isCalled() {
        return callCount != 0;
    }

    public void invoke() {
        callCount++;
    }
}
