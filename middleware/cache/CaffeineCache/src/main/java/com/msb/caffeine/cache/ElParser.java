package com.msb.caffeine.cache;

import com.msb.caffeine.bean.User;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.TreeMap;

public class ElParser {
    public static String parse(String elString, TreeMap<String,Object> map){
        elString=String.format("#{%s}",elString);
        //创建表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        //通过evaluationContext.setVariable可以在上下文中设定变量。
        EvaluationContext context = new StandardEvaluationContext();
        map.entrySet().forEach(entry->
                context.setVariable(entry.getKey(),entry.getValue())
        );

        //解析表达式
        Expression expression = parser.parseExpression(elString, new TemplateParserContext());
        //使用Expression.getValue()获取表达式的值，这里传入了Evaluation上下文
        String value = expression.getValue(context, String.class);
        return value;
    }

    public static void main(String[] args) {
        String elString="#user.name";
        String elString2="#oder";
        String elString3="#p0";

        TreeMap<String,Object> map=new TreeMap<>();
        User user = new User();
        user.setId(111L);
        user.setName("lijin");
        map.put("user",user);
        map.put("oder","oder-8888");

        String val = parse(elString, map);
        String val2 = parse(elString2, map);
        String val3 = parse(elString3, map);
        System.out.println(val);
        System.out.println(val2);
        System.out.println(val3);

    }
}
