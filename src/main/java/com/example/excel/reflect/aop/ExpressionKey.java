package com.example.excel.reflect.aop;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.util.ObjectUtils;

/**
 * @Description:
 * @Author: tangHC
 * @Date: 2023/5/8 18:51
 */
public class ExpressionKey {
    private final AnnotatedElementKey element;
    private final String expression;

    protected ExpressionKey(AnnotatedElementKey element, String expression) {
        this.element = element;
        this.expression = expression;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof ExpressionKey)) {
            return false;
        } else {
            ExpressionKey otherKey = (ExpressionKey)other;
            return this.element.equals(otherKey.element) && ObjectUtils.nullSafeEquals(this.expression, otherKey.expression);
        }
    }

    public int hashCode() {
        return this.element.hashCode() + (this.expression != null ? this.expression.hashCode() * 29 : 0);
    }

    public String toString() {
        return this.element + (this.expression != null ? " with expression \"" + this.expression : "\"");
    }

    public int compareTo(ExpressionKey other) {
        int result = this.element.toString().compareTo(other.element.toString());
        if (result == 0 && this.expression != null) {
            result = this.expression.compareTo(other.expression);
        }

        return result;
    }

    public AnnotatedElementKey getElement() {
        return this.element;
    }

    public String getExpression() {
        return this.expression;
    }
}
