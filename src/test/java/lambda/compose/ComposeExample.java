package lambda.compose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

public class ComposeExample {

    //고차함수, f1, f2라는 두 함수를 인자로 받아 ,f1을 먼저, f2를 나중에 적용하는 새 함수 반환
    public static MyTransFormer compose(MyTransFormer f1, MyTransFormer f2) {
        return s -> f2.trnsForm(f1.trnsForm(s));
    }

    @Test
    void test_compose() {
        MyTransFormer toUpper = s -> s.toUpperCase();
        MyTransFormer addDeco = s -> "**" + s + "**";
        MyTransFormer composeFunc = compose(toUpper, addDeco);

        Assertions.assertEquals("**HELLO**", composeFunc.trnsForm("hello"));
        
    }

}
