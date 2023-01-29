package wiki.appendoc.application.users.port.outbound;

public interface CheckTempAuthDataExistencePort {

    boolean exists(String key);
}
