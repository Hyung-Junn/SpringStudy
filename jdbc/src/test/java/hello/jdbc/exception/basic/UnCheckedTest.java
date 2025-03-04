package hello.jdbc.exception.basic;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unchecked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }


    /**
     * RuntimeException을 상속받은 예외는 언체크 예
     */
    static class MyUncheckedException extends RuntimeException {

        public MyUncheckedException(String message) {
            super(message);
        }
    }

    static class Service {
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리하면 된다.
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                //예외 처리 로직
                log.info("예외 처리, message={}", e.getMessage(), e);

            }
        }

        /**
         * 예외를 잡지 않아도 된다. 자연스럽게 상위로 던짐
         */
        public void callThrow() {
            repository.call();
        }
    }


    /**
     * 언체크 예외는 예외를 잡거나 던지지않아도 된다.
     * 예외를 잡지않으면 자동으로 밖으로 던진다.
     */
    static class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }
}
