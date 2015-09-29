

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

public class NoStaticFieldPredicate<T extends Member> implements Predicate<T> {
    public boolean apply(@Nullable T input) {
        return input != null && !Modifier.isStatic(input.getModifiers());
    }
}
