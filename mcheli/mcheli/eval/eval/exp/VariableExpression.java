//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

import mcheli.eval.eval.lex.*;
import mcheli.eval.eval.*;

public class VariableExpression extends WordExpression
{
    public static AbstractExpression create(final Lex lex, final int prio) {
        final AbstractExpression exp = new VariableExpression(lex.getWord());
        exp.setPos(lex.getString(), lex.getPos());
        exp.setPriority(prio);
        exp.share = lex.getShare();
        return exp;
    }
    
    public VariableExpression(final String str) {
        super(str);
    }
    
    protected VariableExpression(final VariableExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return new VariableExpression(this, s);
    }
    
    public long evalLong() {
        try {
            return this.share.var.evalLong(this.getVarValue());
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2003, this.word, this.string, this.pos, (Throwable)e2);
        }
    }
    
    public double evalDouble() {
        try {
            return this.share.var.evalDouble(this.getVarValue());
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2003, this.word, this.string, this.pos, (Throwable)e2);
        }
    }
    
    public Object evalObject() {
        return this.getVarValue();
    }
    
    protected void let(final Object val, final int pos) {
        final String name = this.getWord();
        try {
            this.share.var.setValue(name, val);
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2102, name, this.string, pos, (Throwable)e2);
        }
    }
    
    private Object getVarValue() {
        final String word = this.getWord();
        Object val;
        try {
            val = this.share.var.getObject(word);
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2101, word, this.string, this.pos, (Throwable)e2);
        }
        if (val == null) {
            throw new EvalException(2103, word, this.string, this.pos, (Throwable)null);
        }
        return val;
    }
    
    protected Object getVariable() {
        try {
            return this.share.var.getObject(this.word);
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2002, this.word, this.string, this.pos, (Throwable)null);
        }
    }
}
