package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {

    @Test
    void test() {
        TypeDescription commandBase = new TypePool.Default.ofClassPath()
            .locate("my.package.CommandBase");
        new ByteBuddy()
            .redefine(commandBase, ClassFileLocator.ForClassLoader.ofClassPath())
            .method(returns(Block.class)).intercept(MethodDelegation.to(CppBlockInterceptor.class))
            .make()
            .load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTOR);

        CppBlockInterceptor.getBlockByText(null, "1").getLocalizedName();
    }


    public static class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }
}
