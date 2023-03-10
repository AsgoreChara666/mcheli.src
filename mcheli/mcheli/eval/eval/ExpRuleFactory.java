//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval;

import mcheli.eval.eval.lex.*;
import mcheli.eval.eval.rule.*;
import mcheli.eval.eval.exp.*;

public class ExpRuleFactory
{
    private static ExpRuleFactory me;
    protected Rule rule;
    protected AbstractRule topRule;
    protected LexFactory defaultLexFactory;
    
    public static ExpRuleFactory getInstance() {
        if (ExpRuleFactory.me == null) {
            ExpRuleFactory.me = new ExpRuleFactory();
        }
        return ExpRuleFactory.me;
    }
    
    public static Rule getDefaultRule() {
        return getInstance().getRule();
    }
    
    public static Rule getJavaRule() {
        return JavaRuleFactory.getInstance().getRule();
    }
    
    public ExpRuleFactory() {
        final ShareRuleValue share = new ShareRuleValue();
        share.lexFactory = this.getLexFactory();
        this.init(share);
        this.rule = share;
    }
    
    public Rule getRule() {
        return this.rule;
    }
    
    protected void init(final ShareRuleValue share) {
        AbstractRule rule = null;
        rule = this.add(rule, this.createCommaRule(share));
        rule = this.add(rule, this.createLetRule(share));
        rule = this.add(rule, this.createIfRule(share));
        rule = this.add(rule, this.createOrRule(share));
        rule = this.add(rule, this.createAndRule(share));
        rule = this.add(rule, this.createBitOrRule(share));
        rule = this.add(rule, this.createBitXorRule(share));
        rule = this.add(rule, this.createBitAndRule(share));
        rule = this.add(rule, this.createEqualRule(share));
        rule = this.add(rule, this.createGreaterRule(share));
        rule = this.add(rule, this.createShiftRule(share));
        rule = this.add(rule, this.createPlusRule(share));
        rule = this.add(rule, this.createMultRule(share));
        rule = this.add(rule, this.createSignRule(share));
        rule = this.add(rule, this.createPowerRule(share));
        rule = this.add(rule, this.createCol1AfterRule(share));
        rule = this.add(rule, this.createPrimaryRule(share));
        this.topRule.initPriority(1);
        share.topRule = this.topRule;
        this.initFuncArgRule(share);
    }
    
    protected void initFuncArgRule(final ShareRuleValue share) {
        final AbstractRule funcArgRule = this.createFuncArgRule(share);
        share.funcArgRule = funcArgRule;
        final AbstractRule argRule = funcArgRule;
        final String[] a_opes = argRule.getOperators();
        final String[] t_opes = this.topRule.getOperators();
        boolean match = false;
    Label_0082:
        for (int i = 0; i < a_opes.length; ++i) {
            for (int j = 0; j < t_opes.length; ++j) {
                if (a_opes[i].equals(t_opes[j])) {
                    match = true;
                    break Label_0082;
                }
            }
        }
        if (match) {
            argRule.nextRule = this.topRule.nextRule;
        }
        else {
            argRule.nextRule = this.topRule;
        }
        argRule.prio = this.topRule.prio;
    }
    
    protected final AbstractRule add(final AbstractRule rule, final AbstractRule r) {
        if (r == null) {
            return rule;
        }
        if (this.topRule == null) {
            this.topRule = r;
        }
        if (rule != null) {
            rule.nextRule = r;
        }
        return r;
    }
    
    protected AbstractRule createCommaRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createCommaExpression());
        return me;
    }
    
    protected AbstractExpression createCommaExpression() {
        return (AbstractExpression)new CommaExpression();
    }
    
    protected AbstractRule createLetRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2RightJoinRule(share);
        me.addExpression(this.createLetExpression());
        me.addExpression(this.createLetMultExpression());
        me.addExpression(this.createLetDivExpression());
        me.addExpression(this.createLetModExpression());
        me.addExpression(this.createLetPlusExpression());
        me.addExpression(this.createLetMinusExpression());
        me.addExpression(this.createLetShiftLeftExpression());
        me.addExpression(this.createLetShiftRightExpression());
        me.addExpression(this.createLetShiftRightLogicalExpression());
        me.addExpression(this.createLetAndExpression());
        me.addExpression(this.createLetOrExpression());
        me.addExpression(this.createLetXorExpression());
        me.addExpression(this.createLetPowerExpression());
        return me;
    }
    
    protected AbstractExpression createLetExpression() {
        return (AbstractExpression)new LetExpression();
    }
    
    protected AbstractExpression createLetMultExpression() {
        return (AbstractExpression)new LetMultExpression();
    }
    
    protected AbstractExpression createLetDivExpression() {
        return (AbstractExpression)new LetDivExpression();
    }
    
    protected AbstractExpression createLetModExpression() {
        return (AbstractExpression)new LetModExpression();
    }
    
    protected AbstractExpression createLetPlusExpression() {
        return (AbstractExpression)new LetPlusExpression();
    }
    
    protected AbstractExpression createLetMinusExpression() {
        return (AbstractExpression)new LetMinusExpression();
    }
    
    protected AbstractExpression createLetShiftLeftExpression() {
        return (AbstractExpression)new LetShiftLeftExpression();
    }
    
    protected AbstractExpression createLetShiftRightExpression() {
        return (AbstractExpression)new LetShiftRightExpression();
    }
    
    protected AbstractExpression createLetShiftRightLogicalExpression() {
        return (AbstractExpression)new LetShiftRightLogicalExpression();
    }
    
    protected AbstractExpression createLetAndExpression() {
        return (AbstractExpression)new LetAndExpression();
    }
    
    protected AbstractExpression createLetOrExpression() {
        return (AbstractExpression)new LetOrExpression();
    }
    
    protected AbstractExpression createLetXorExpression() {
        return (AbstractExpression)new LetXorExpression();
    }
    
    protected AbstractExpression createLetPowerExpression() {
        return (AbstractExpression)new LetPowerExpression();
    }
    
    protected AbstractRule createIfRule(final ShareRuleValue share) {
        final IfRule me = new IfRule(share);
        me.addExpression(me.cond = this.createIfExpression());
        return me;
    }
    
    protected AbstractExpression createIfExpression() {
        return (AbstractExpression)new IfExpression();
    }
    
    protected AbstractRule createOrRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createOrExpression());
        return me;
    }
    
    protected AbstractExpression createOrExpression() {
        return (AbstractExpression)new OrExpression();
    }
    
    protected AbstractRule createAndRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createAndExpression());
        return me;
    }
    
    protected AbstractExpression createAndExpression() {
        return (AbstractExpression)new AndExpression();
    }
    
    protected AbstractRule createBitOrRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createBitOrExpression());
        return me;
    }
    
    protected AbstractExpression createBitOrExpression() {
        return (AbstractExpression)new BitOrExpression();
    }
    
    protected AbstractRule createBitXorRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createBitXorExpression());
        return me;
    }
    
    protected AbstractExpression createBitXorExpression() {
        return (AbstractExpression)new BitXorExpression();
    }
    
    protected AbstractRule createBitAndRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createBitAndExpression());
        return me;
    }
    
    protected AbstractExpression createBitAndExpression() {
        return (AbstractExpression)new BitAndExpression();
    }
    
    protected AbstractRule createEqualRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createEqualExpression());
        me.addExpression(this.createNotEqualExpression());
        return me;
    }
    
    protected AbstractExpression createEqualExpression() {
        return (AbstractExpression)new EqualExpression();
    }
    
    protected AbstractExpression createNotEqualExpression() {
        return (AbstractExpression)new NotEqualExpression();
    }
    
    protected AbstractRule createGreaterRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createLessThanExpression());
        me.addExpression(this.createLessEqualExpression());
        me.addExpression(this.createGreaterThanExpression());
        me.addExpression(this.createGreaterEqualExpression());
        return me;
    }
    
    protected AbstractExpression createLessThanExpression() {
        return (AbstractExpression)new LessThanExpression();
    }
    
    protected AbstractExpression createLessEqualExpression() {
        return (AbstractExpression)new LessEqualExpression();
    }
    
    protected AbstractExpression createGreaterThanExpression() {
        return (AbstractExpression)new GreaterThanExpression();
    }
    
    protected AbstractExpression createGreaterEqualExpression() {
        return (AbstractExpression)new GreaterEqualExpression();
    }
    
    protected AbstractRule createShiftRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createShiftLeftExpression());
        me.addExpression(this.createShiftRightExpression());
        me.addExpression(this.createShiftRightLogicalExpression());
        return me;
    }
    
    protected AbstractExpression createShiftLeftExpression() {
        return (AbstractExpression)new ShiftLeftExpression();
    }
    
    protected AbstractExpression createShiftRightExpression() {
        return (AbstractExpression)new ShiftRightExpression();
    }
    
    protected AbstractExpression createShiftRightLogicalExpression() {
        return (AbstractExpression)new ShiftRightLogicalExpression();
    }
    
    protected AbstractRule createPlusRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createPlusExpression());
        me.addExpression(this.createMinusExpression());
        return me;
    }
    
    protected AbstractExpression createPlusExpression() {
        return (AbstractExpression)new PlusExpression();
    }
    
    protected AbstractExpression createMinusExpression() {
        return (AbstractExpression)new MinusExpression();
    }
    
    protected AbstractRule createMultRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createMultExpression());
        me.addExpression(this.createDivExpression());
        me.addExpression(this.createModExpression());
        return me;
    }
    
    protected AbstractExpression createMultExpression() {
        return (AbstractExpression)new MultExpression();
    }
    
    protected AbstractExpression createDivExpression() {
        return (AbstractExpression)new DivExpression();
    }
    
    protected AbstractExpression createModExpression() {
        return (AbstractExpression)new ModExpression();
    }
    
    protected AbstractRule createSignRule(final ShareRuleValue share) {
        final AbstractRule me = new SignRule(share);
        me.addExpression(this.createSignPlusExpression());
        me.addExpression(this.createSignMinusExpression());
        me.addExpression(this.createBitNotExpression());
        me.addExpression(this.createNotExpression());
        me.addExpression(this.createIncBeforeExpression());
        me.addExpression(this.createDecBeforeExpression());
        return me;
    }
    
    protected AbstractExpression createSignPlusExpression() {
        return (AbstractExpression)new SignPlusExpression();
    }
    
    protected AbstractExpression createSignMinusExpression() {
        return (AbstractExpression)new SignMinusExpression();
    }
    
    protected AbstractExpression createBitNotExpression() {
        return (AbstractExpression)new BitNotExpression();
    }
    
    protected AbstractExpression createNotExpression() {
        return (AbstractExpression)new NotExpression();
    }
    
    protected AbstractExpression createIncBeforeExpression() {
        return (AbstractExpression)new IncBeforeExpression();
    }
    
    protected AbstractExpression createDecBeforeExpression() {
        return (AbstractExpression)new DecBeforeExpression();
    }
    
    protected AbstractRule createPowerRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createPowerExpression());
        return me;
    }
    
    protected AbstractExpression createPowerExpression() {
        return (AbstractExpression)new PowerExpression();
    }
    
    protected AbstractRule createCol1AfterRule(final ShareRuleValue share) {
        final Col1AfterRule me = new Col1AfterRule(share);
        me.addExpression(me.func = this.createFunctionExpression());
        me.addExpression(me.array = this.createArrayExpression());
        me.addExpression(this.createIncAfterExpression());
        me.addExpression(this.createDecAfterExpression());
        me.addExpression(me.field = this.createFieldExpression());
        return me;
    }
    
    protected AbstractExpression createFunctionExpression() {
        return (AbstractExpression)new FunctionExpression();
    }
    
    protected AbstractExpression createArrayExpression() {
        return (AbstractExpression)new ArrayExpression();
    }
    
    protected AbstractExpression createIncAfterExpression() {
        return (AbstractExpression)new IncAfterExpression();
    }
    
    protected AbstractExpression createDecAfterExpression() {
        return (AbstractExpression)new DecAfterExpression();
    }
    
    protected AbstractExpression createFieldExpression() {
        return (AbstractExpression)new FieldExpression();
    }
    
    protected AbstractRule createPrimaryRule(final ShareRuleValue share) {
        final AbstractRule me = new PrimaryRule(share);
        me.addExpression(this.createParenExpression());
        return me;
    }
    
    protected AbstractExpression createParenExpression() {
        return (AbstractExpression)new ParenExpression();
    }
    
    protected AbstractRule createFuncArgRule(final ShareRuleValue share) {
        final AbstractRule me = new Col2Rule(share);
        me.addExpression(this.createFuncArgExpression());
        return me;
    }
    
    protected AbstractExpression createFuncArgExpression() {
        return (AbstractExpression)new FuncArgExpression();
    }
    
    protected LexFactory getLexFactory() {
        if (this.defaultLexFactory == null) {
            this.defaultLexFactory = new LexFactory();
        }
        return this.defaultLexFactory;
    }
}
