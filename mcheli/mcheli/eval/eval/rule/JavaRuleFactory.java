//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.rule;

import mcheli.eval.eval.*;
import mcheli.eval.eval.exp.*;

public class JavaRuleFactory extends ExpRuleFactory
{
    private static JavaRuleFactory me;
    
    public static ExpRuleFactory getInstance() {
        if (JavaRuleFactory.me == null) {
            JavaRuleFactory.me = new JavaRuleFactory();
        }
        return JavaRuleFactory.me;
    }
    
    protected AbstractRule createCommaRule(final ShareRuleValue share) {
        return null;
    }
    
    protected AbstractRule createPowerRule(final ShareRuleValue share) {
        return null;
    }
    
    protected AbstractExpression createLetPowerExpression() {
        return null;
    }
}
