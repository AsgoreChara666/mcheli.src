//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.exp;

import mcheli.eval.eval.*;

public class ArrayExpression extends Col2OpeExpression
{
    public ArrayExpression() {
        this.setOperator("[");
        this.setEndOperator("]");
    }
    
    protected ArrayExpression(final ArrayExpression from, final ShareExpValue s) {
        super(from, s);
    }
    
    public AbstractExpression dup(final ShareExpValue s) {
        return new ArrayExpression(this, s);
    }
    
    @Override
    public long evalLong() {
        try {
            return this.share.var.evalLong(this.getVariable());
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2201, this.toString(), this.string, this.pos, (Throwable)e2);
        }
    }
    
    @Override
    public double evalDouble() {
        try {
            return this.share.var.evalDouble(this.getVariable());
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2201, this.toString(), this.string, this.pos, (Throwable)e2);
        }
    }
    
    @Override
    public Object evalObject() {
        return this.getVariable();
    }
    
    protected Object getVariable() {
        final Object obj = this.expl.getVariable();
        if (obj == null) {
            throw new EvalException(2104, this.expl.toString(), this.string, this.pos, (Throwable)null);
        }
        final int index = (int)this.expr.evalLong();
        try {
            return this.share.var.getObject(obj, index);
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2201, this.toString(), this.string, this.pos, (Throwable)e2);
        }
    }
    
    protected void let(final Object val, final int pos) {
        final Object obj = this.expl.getVariable();
        if (obj == null) {
            throw new EvalException(2104, this.expl.toString(), this.string, pos, (Throwable)null);
        }
        final int index = (int)this.expr.evalLong();
        try {
            this.share.var.setValue(obj, index, val);
        }
        catch (EvalException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new EvalException(2202, this.toString(), this.string, pos, (Throwable)e2);
        }
    }
    
    @Override
    protected AbstractExpression replaceVar() {
        this.expl = this.expl.replaceVar();
        this.expr = this.expr.replace();
        return this.share.repl.replaceVar2(this);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(this.expl.toString());
        sb.append('[');
        sb.append(this.expr.toString());
        sb.append(']');
        return sb.toString();
    }
}
