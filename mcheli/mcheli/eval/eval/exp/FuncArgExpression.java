//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

import java.util.*;

public class FuncArgExpression extends Col2OpeExpression
{
    public FuncArgExpression() {
        this.setOperator(",");
    }
    
    protected FuncArgExpression(final FuncArgExpression from, final ShareExpValue s) {
        super((Col2Expression)from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return (AbstractExpression)new FuncArgExpression(this, s);
    }
    
    protected void evalArgsLong(final List args) {
        this.expl.evalArgsLong(args);
        this.expr.evalArgsLong(args);
    }
    
    protected void evalArgsDouble(final List args) {
        this.expl.evalArgsDouble(args);
        this.expr.evalArgsDouble(args);
    }
    
    protected void evalArgsObject(final List args) {
        this.expl.evalArgsObject(args);
        this.expr.evalArgsObject(args);
    }
    
    protected String toStringLeftSpace() {
        return "";
    }
}
